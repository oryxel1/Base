package com.bascenario.sprite;

import com.bascenario.render.manager.TextureManager;
import com.bascenario.render.manager.api.TextureKey;
import com.bascenario.util.math.Vec2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import org.lwjgl.opengl.GL11C;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class Sprite {
    private final String name;
    private final Map<String, TextureKey> nameToTexture = new HashMap<>();
    private final Map<String, AtlasInfo> nameToAtlas = new HashMap<>();

    // TODO: There is probably a better way to do this.
    @SneakyThrows
    public static Sprite build(String name, File atlas, File imageFile) {
        final Sprite sprite = new Sprite(name);

        final List<String> lines = Files.readAllLines(Path.of(atlas.getPath()));

        Vec2 globalSize = null;
        String cacheName = "";
        boolean rotate = false;
        Vec2 position = null, size = null, origin = null, offset = null;
        for (String line : lines) {
            line = line.toLowerCase(Locale.ROOT);
            if (line.replace(" ", "").isEmpty() || line.endsWith(".png")) {
                continue;
            }
            // we don't really need these info, eg:
            // format: RGBA8888
            // filter: Linear,Linear
            // repeat: none
            if (line.startsWith("format:") || line.startsWith("filter:") || line.startsWith("repeat:")) {
                continue;
            }

            if (line.startsWith("size:") && cacheName.isEmpty()) {
                String[] split = line.replace("size:", "").replace(" ", "").split(",");
                globalSize = new Vec2(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                continue;
            }

            if (!line.contains(":")) {
                if (!cacheName.isEmpty()) {
                    sprite.nameToAtlas.put(cacheName, new AtlasInfo(cacheName, rotate, position, size, origin, offset));
                }

                cacheName = line;
                continue;
            }

            if (cacheName.isEmpty()) {
                continue;
            }

            line = line.replace(" ", "");
            if (line.startsWith("rotate:")) {
                rotate = Boolean.parseBoolean(line.replace("rotate:", "").replace(" ", ""));
            } else if (line.startsWith("xy:")) {
                String[] split = line.replace("xy:", "").replace(" ", "").split(",");
                position = new Vec2(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            } else if (line.startsWith("size:")) {
                String[] split = line.replace("size:", "").replace(" ", "").split(",");
                size = new Vec2(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            } else if (line.startsWith("orig:")) {
                String[] split = line.replace("orig:", "").replace(" ", "").split(",");
                origin = new Vec2(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            } else if (line.startsWith("offset:")) {
                String[] split = line.replace("offset:", "").replace(" ", "").split(",");
                offset = new Vec2(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        }

        if (!cacheName.isEmpty()) {
            sprite.nameToAtlas.put(cacheName, new AtlasInfo(cacheName, rotate, position, size, origin, offset));
        }

        sprite.nameToAtlas.values().forEach(System.out::println);

        // Insanely inefficient but it works so it works.
        for (AtlasInfo info : sprite.nameToAtlas.values()) {
            final Texture2D fullTexture = Texture2D.fromImage(Files.readAllBytes(imageFile.toPath()));

            int minX = info.position.x(), minY = info.position.y(), maxX = minX + info.size().x(), maxY = minY + info.size().y();
            if (info.rotate()) {
                maxX = minX + info.size().y();
                maxY = minY + info.size().x();
            }

            for (AtlasInfo other : sprite.nameToAtlas.values()) {
                if (other == info) {
                    continue;
                }

                int minX1 = other.position.x(), minY1 = other.position.y(), maxX1 = minX1 + other.size().x(), maxY1 = minY1 + other.size().y();
                if (other.rotate()) {
                    maxX1 = minX1 + other.size().y();
                    maxY1 = minY1 + other.size().x();
                }

                if (info.size.x() + info.size.y() < other.size.x() + other.size.y()) {
//                    System.out.println("skip: " + info + "," + other);
                    continue;
                }

                if (minX1 >= minX || minY1 >= minY) {
                    fullTexture.clear(minX1, minY1, maxX1 - minX1, maxY1 - minY1, Color.TRANSPARENT);
//                    System.out.println(info.name + "," + other.name);
                }
            }

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(fullTexture.downloadPngImageBytes(0, 0, globalSize.x(), globalSize.y(), GL11C.GL_RGBA)));
//            System.out.println("Name: " + info.name);
//            System.out.println(minX + "," + minY + "," + info.size().x() + ',' + info.size().y());
            image = image.getSubimage(minX, minY, maxX - minX, maxY - minY);

            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);

            // Uncomment this code to test.
//            Files.write(Path.of("C:\\Users\\PC\\Documents\\BAScenarioEngine\\run\\" + info.name + ".png"), stream.toByteArray());

            Texture2D texture2D = Texture2D.fromImage(stream.toByteArray());
            final TextureKey key = new TextureKey("sprite_in_memory_" + name + "_" + info.name, texture2D);
            sprite.nameToTexture.put(info.name(), key);
            TextureManager.getInstance().getTextures().put(key.path(), key);
        }

        return sprite;
    }

    private record AtlasInfo(String name, boolean rotate, Vec2 position, Vec2 size, Vec2 origin, Vec2 offset) {}
}
