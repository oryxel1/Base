package oxy.bascenario.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.SkeletonBinary;
import com.esotericsoftware.spine.SkeletonData;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import net.raphimc.audiomixer.io.AudioIO;
import net.raphimc.audiomixer.io.mp3.Mp3InputStream;
import net.raphimc.audiomixer.io.ogg.OggVorbisInputStream;
import net.raphimc.audiomixer.util.MathUtil;
import net.raphimc.audiomixer.util.PcmFloatAudioFormat;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.api.managers.AssetsManagerApi;
import oxy.bascenario.api.managers.other.AssetType;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.managers.other.AudioAsset;
import oxy.bascenario.managers.other.GifAsset;
import oxy.bascenario.managers.other.TextureAsset;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.utils.files.FileUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

@SuppressWarnings("ALL")
public class AssetsManager implements AssetsManagerApi {
    private static int INVALID_TEXTURE_KEY = Integer.MIN_VALUE;

    private final Map<Integer, Asset<?>> assets;
    public Collection<Asset<?>> assets() {
        return this.assets.values();
    }
    
    private final Set<Integer> currentlyLoadingAssets;

    public AssetsManager() {
        this.assets = new HashMap<>();
        this.currentlyLoadingAssets = Collections.synchronizedSet(new HashSet<>());

        // Pre-load some stuff.
        assets(null, FileInfo.internal("assets/base/sounds/click-sound.mp3"));
    }

    // We no longer do this, but I'll keep this here anyway.
//    public void load() {
//        for (Scenario scenario : Base.instance().scenarioManager().scenarios()) {
//            final Path path = new File(new File(ScenarioManager.SAVE_DIR, scenario.getName()), "files").toPath();
//
//            try (final Stream<Path> stream = Files.walk(path)) {
//                List<File> files = stream.filter(Files::isRegularFile).map(Path::toFile).toList();
//
//                for (File file : files) {
//                    FileInfo info = new FileInfo(file.getAbsolutePath().replace(path.toFile().getAbsolutePath() + "\\", ""), false, false);
//                    load(scenario.getName(), info, true);
//                }
//            }  catch (IOException ignored) {
//            }
//        }
//    }

    public Texture2D texture(String path) {
        return texture(null, new FileInfo(path, false, true));
    }

    public Texture2D texture(FileInfo info) {
        return texture(null, info);
    }

    public Texture2D texture(String scenario, FileInfo info) {
        try {
            return ((TextureAsset)assets(scenario, info, AssetType.TEXTURE).asset()).get();
        } catch (Exception ignored) {
            if (INVALID_TEXTURE_KEY == Integer.MIN_VALUE) {
                FileInfo invalidTexture = new FileInfo("assets/base/invalid.png", false, true);
                INVALID_TEXTURE_KEY = invalidTexture.hashCode(null);
                load(null, invalidTexture, AssetType.TEXTURE);
            }

            return ((TextureAsset)this.assets.get(INVALID_TEXTURE_KEY).asset()).get();
        }
    }

    @Override
    public <T> Asset<T> assets(String scenario, FileInfo info, AssetType type) {
        Asset<?> asset = this.assets.get(info.hashCode(scenario));
        if (asset == null) {
            load(scenario, info, type);
            asset = this.assets.get(info.hashCode(scenario));
        }

        return (Asset<T>) asset;
    }

    @Override
    public <T> T get(String scenario, FileInfo info, AssetType type) {
        return (T) assets(scenario, info, type).asset();
    }

    @SneakyThrows
    public void load(String scenario, FileInfo info, AssetType type) {
        if (this.currentlyLoadingAssets.contains(info.hashCode(scenario))) {
            return;
        }

        // Try to find the asset type if it's not defined...
        if (type == AssetType.UNKNOWN) {
            final String path = info.path().toLowerCase(Locale.ROOT);
            if (path.endsWith(".mp3") || path.endsWith(".ogg") || path.endsWith(".wav")) {
                type = AssetType.AUDIO;
            }else if (path.endsWith(".gif")) {
                type = AssetType.GIF;
            } else if (path.endsWith(".png") || path.endsWith(".jpg")) {
                type = AssetType.TEXTURE;
            } else if (path.endsWith(".ttf")) {
                type = AssetType.TTF;
            }
        }

        this.currentlyLoadingAssets.add(info.hashCode(scenario));

        final InputStream stream = type == AssetType.UNKNOWN || type == AssetType.SKELETON ? null : FileUtils.toStream(scenario, info);
        switch (type) {
            case AUDIO -> loadAudio(scenario, stream, info);
            case GIF -> this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, new GifAsset(stream.readAllBytes())));
            case TEXTURE -> this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, new TextureAsset(stream.readAllBytes())));
            case TTF -> this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, stream.readAllBytes()));
            case ATLAS -> this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, new TextureAtlas(FileUtils.toHandle(scenario, info))));
            case SKELETON -> {
                final JsonObject object = JsonParser.parseString(info.path().trim()).getAsJsonObject();
                final FileInfo atlas = Types.FILE_INFO_TYPE.read(object.get("first"));
                final FileInfo skeleton = Types.FILE_INFO_TYPE.read(object.get("second"));

                SkeletonData skeletonData = new SkeletonBinary((TextureAtlas) get(scenario, atlas, AssetType.ATLAS))
                        .readSkeletonData(FileUtils.toHandle(scenario, skeleton));

                this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, skeletonData));
            }
        }

        this.currentlyLoadingAssets.remove(info.hashCode(scenario));
    }

    private boolean loadAudio(String scenario, InputStream stream, FileInfo info) {
        try {
            final AudioInputStream audioInputStream;
            final String path = info.path().toLowerCase(Locale.ROOT);
            if (path.endsWith(".mp3")) {
                audioInputStream = Mp3InputStream.createAudioInputStream(stream);
            } else if (path.endsWith(".ogg")) {
                audioInputStream = OggVorbisInputStream.createAudioInputStream(stream);
            } else if (path.endsWith(".wav")) {
                audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(stream));
            } else {
                return false;
            }

            this.currentlyLoadingAssets.add(info.hashCode(scenario));
            float[] samples = AudioIO.readSamples(audioInputStream, new PcmFloatAudioFormat(48000, 2));
            this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, new AudioAsset(samples, audioInputStream.getFormat(), (long) MathUtil.sampleCountToMillis(new PcmFloatAudioFormat(audioInputStream.getFormat()), samples.length))));
            this.currentlyLoadingAssets.remove(info.hashCode(scenario));
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}
