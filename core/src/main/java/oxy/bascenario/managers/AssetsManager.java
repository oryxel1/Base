package oxy.bascenario.managers;

import lombok.SneakyThrows;
import net.raphimc.audiomixer.io.AudioIO;
import net.raphimc.audiomixer.io.mp3.Mp3InputStream;
import net.raphimc.audiomixer.io.ogg.OggVorbisInputStream;
import net.raphimc.audiomixer.util.MathUtil;
import net.raphimc.audiomixer.util.PcmFloatAudioFormat;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.image.animated.impl.AwtGifImage;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.managers.AssetsManagerApi;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.managers.other.AudioAsset;
import oxy.bascenario.utils.FileUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

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
        load();
    }

    public void load() {
        // This is purely only for audio, considering that it's quite slow to load and cache these.
        // The rest like texture can be load very quickly so we don't really need to worry about those.
        new Thread(() -> {
            for (Scenario scenario : Base.instance().scenarioManager().scenarios()) {
                final Path path = new File(new File(ScenarioManager.SAVE_DIR, scenario.getName()), "files").toPath();

                try (final Stream<Path> stream = Files.walk(path)) {
                    List<File> files = stream.filter(Files::isRegularFile).map(Path::toFile).toList();

                    for (File file : files) {
                        final FileInfo info = new FileInfo(file.getName(), false, false);
                        final InputStream inputStream = FileUtils.toStream(scenario.getName(), info);
                        loadAudio(scenario.getName(), inputStream, info);
                    }
                } catch (IOException ignored) {
                }
            }
        }).start();
    }

    public Texture2D texture(String path) {
        return texture(null, new FileInfo(path, false, true));
    }

    public Texture2D texture(FileInfo info) {
        return texture(null, info);
    }

    public Texture2D texture(String scenario, FileInfo info) {
        try {
            return (Texture2D) assets(info).asset();
        } catch (Exception ignored) {
            if (INVALID_TEXTURE_KEY == Integer.MIN_VALUE) {
                FileInfo invalidTexture = new FileInfo("assets/base/invalid.png", false, true);
                INVALID_TEXTURE_KEY = invalidTexture.hashCode(null);
                load(null, invalidTexture);
            }

            return (Texture2D) this.assets.get(INVALID_TEXTURE_KEY).asset();
        }
    }

    @Override
    public <T> Asset<T> assets(String scenario, FileInfo info) {
        Asset<?> asset = this.assets.get(info.hashCode(scenario));
        if (asset == null) {
            load(scenario, info);
            asset = this.assets.get(info.hashCode(scenario));
        }

        return (Asset<T>) asset;
    }

    @Override
    public <T> T get(String scenario, FileInfo info) {
        return (T) assets(info).asset();
    }

    @SneakyThrows
    public void load(String scenario, FileInfo info) {
        if (this.currentlyLoadingAssets.contains(info.hashCode(scenario))) {
            return;
        }

        this.currentlyLoadingAssets.add(info.hashCode(scenario));
        final InputStream stream = FileUtils.toStream(scenario, info);
        loadAudio(scenario, stream, info);

        final String path = info.path().toLowerCase(Locale.ROOT);
        if (path.endsWith(".gif")) {
            this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, new AwtGifImage(stream)));
        } else if (path.endsWith(".png") || path.endsWith(".jpg")) {
            this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, Texture2D.fromImage(stream.readAllBytes())));
        } else if (path.endsWith(".ttf")) {
            this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, stream.readAllBytes()));
        }
        this.currentlyLoadingAssets.remove(info.hashCode(scenario));
    }

    private void loadAudio(String scenario, InputStream stream, FileInfo info) {
        this.currentlyLoadingAssets.add(info.hashCode(scenario));
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
                return;
            }

            float[] samples = AudioIO.readSamples(audioInputStream, new PcmFloatAudioFormat(48000, 2));
            this.assets.put(info.hashCode(scenario), new Asset<>(scenario, info, new AudioAsset(samples, (long) MathUtil.sampleCountToMillis(new PcmFloatAudioFormat(audioInputStream.getFormat()), samples.length))));
            this.currentlyLoadingAssets.remove(info.hashCode(scenario));
        } catch (Exception ignored) {
        }
    }
}
