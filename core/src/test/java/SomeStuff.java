import net.lenni0451.commons.color.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SomeStuff {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Computer\\BAAS\\Effects\\FX_TEX_SCN_Ring_02.png"));

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = Color.fromARGB(image.getRGB(x, y));
                Color newColor = Color.fromRGBA(
                        255, 255, 255, Math.round((color.getRed() + color.getGreen() + color.getBlue()) / 3f)
                );
                image.setRGB(x, y, newColor.toARGB());
            }
        }

        ImageIO.write(image, "png", new File("FX_TEX_SCN_Ring_02.png"));
    }
}
