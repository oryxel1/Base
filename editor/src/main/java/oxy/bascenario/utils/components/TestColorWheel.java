package oxy.bascenario.utils.components;

import lombok.Getter;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.resource.image.impl.ByteImage2D;
import org.lwjgl.opengl.GL11C;

// https://blog.stylingandroid.com/colour-wheel-part-1/
public class TestColorWheel extends Component {
    private ThinGLTexture colorWheel;

    private boolean yay;

    @Getter
    private Color color = Color.RED;
    @Override
    public void render(Renderer renderer, Size size) {
        if (!yay) {
            final ByteImage2D image = new ByteImage2D(200, 200, GL11C.GL_RGBA);
            image.clear();

            float centreX = image.getWidth() / 2.0f;
            float centreY = image.getHeight() / 2.0f;
            float radius = Math.min(centreX, centreY);

            double xOffset, yOffset, centreOffset, centreAngle;
            Color colour;
            float[] hsv = new float[] {0f, 0f, 0f};
            hsv[2] = 1f;

            for (int x = 0; x < image.getWidth(); x++) { for (int y = 0; y < image.getHeight(); y++) {
                xOffset = x - centreX;
                yOffset = y - centreY;
                centreOffset = Math.hypot(xOffset, yOffset);
                if (centreOffset <= radius) {
                    centreAngle = (Math.toDegrees(Math.atan2((yOffset), (xOffset))) + 360.0) % 360.0;
                    hsv[0] = (float) centreAngle / 360f;
                    hsv[1] = (float) (centreOffset / radius);
                    colour = Color.fromHSB(hsv);

                    image.getPixels().putInt((y * image.getWidth() + x) * 4, colour.toARGB());
                }
            }}

            colorWheel = new ThinGLTexture(Texture2D.fromImage(image));
            yay = true;
            image.free();
        }

        renderer.image(colorWheel, 0, 0, size.width(), 200, Color.WHITE);

        renderer.fillRect(0, 205, 100, 100, color);

        float[] hsb = color.toHSB();

        float centreX = size.width() / 2.0f;
        float centreY = 200 / 2.0f;
        float radius = Math.min(centreX, centreY);

        float centreAngle = hsb[0] * 360f;
        float centreOffset = hsb[1] * radius;

        double x = centreOffset * (Math.cos(Math.toRadians(centreAngle)));
        double y = Math.sqrt((centreOffset * centreOffset) - (x * x)) * Math.signum(Math.sin(Math.toRadians(centreAngle)));

        x += centreX;
        y += centreY;

        renderer.fillRect((float) x, (float) y, 10, 10, Color.BLACK);
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        if (event.isHeld(MouseButton.LEFT)) {
            update(size, event.x(), event.y());
        }
        return super.onComponentMouseMove(event, size);
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        update(size, event.x(), event.y());
        return true;
    }

    // Using the same math that generated the image to determine the color.
    private void update(Size size, float x, float y) {
        float centreX = size.width() / 2.0f;
        float centreY = 200 / 2.0f;
        float radius = Math.min(centreX, centreY);

        double xOffset, yOffset, centreOffset, centreAngle;

        xOffset = x - centreX;
        yOffset = y - centreY;
        centreOffset = Math.hypot(xOffset, yOffset);
        if (centreOffset <= radius) {
            centreAngle = (Math.toDegrees(Math.atan2(yOffset, xOffset)) + 360.0) % 360.0;
            float hue = (float) centreAngle / 360f;
            float saturation = (float) (centreOffset / radius);
            float brightness = 1;
            color = Color.fromHSB(hue, saturation, brightness);
        }
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(200, 500);
    }
}
