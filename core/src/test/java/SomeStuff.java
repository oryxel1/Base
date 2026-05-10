import net.lenni0451.commons.color.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SomeStuff {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Computer\\Pictures\\Screenshots\\Screenshot (12).png"));

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = Color.fromARGB(newImage.getRGB(x, y));
                if (color.getRed() > 180 && color.getGreen() > 180 && color.getBlue() > 180) {
                    newImage.setRGB(x, y, color.toARGB());
                    continue;
                }

                Color newColor = Color.fromRGBA(
                        244, 245, 246, Math.round((color.getRed() + color.getGreen() + color.getBlue()) / 3f)
                );
                newImage.setRGB(x, y, newColor.toARGB());
            }
        }

        ImageIO.write(newImage, "png", new File("stuff.png"));
    }
}
