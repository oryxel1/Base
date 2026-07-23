package oxy.base.utils.components;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.ListenerList;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.resource.image.impl.ByteImage2D;
import org.lwjgl.opengl.GL12C;

import java.util.function.Consumer;

// https://blog.stylingandroid.com/colour-wheel-part-1/
@Accessors(fluent = true)
public class ColorWheelPicker extends Component {
    @Setter
    private float gap = 8f;
    @Setter
    private int outlineWidth = 2;
    @Setter
    private float sliderWidth = 15;

    private int wheelSize = 160;

    private float brightness = 1;

    private ThinGLTexture generatedTexture;

    @Getter
    private final ListenerList<Consumer<Color>> colorChangeListener = new ListenerList<>();

    @Setter
    private Color color;
    public void color(Color color) {
        float[] hsb = color.toHSB();
        this.color = Color.fromHSB(hsb[0], hsb[1], 1).withAlpha(color.getAlpha());
        this.brightness = hsb[2];
    }

    public Color color() {
        float[] hsb = color.toHSB();
        return Color.fromHSB(hsb[0], hsb[1], brightness).withAlpha(color.getAlpha());
    }

    public ColorWheelPicker(Color color) {
        this.color = color;
    }

    @Override
    public void render(Renderer renderer, Size size) {
        if (generatedTexture == null) {
            generate();
        }

        renderer.image(generatedTexture, 0, 0, wheelSize - outlineWidth, wheelSize - outlineWidth, Color.WHITE);

        final float centreX = wheelSize / 2f, centreY = wheelSize / 2f;
        final float radius = Math.min(centreX, centreY);

        renderer.fillCircle(centreX, centreY, radius, Color.BLACK.withAlphaF(1 - brightness)); // Hack.

        renderer.outlineCircle(centreX, centreY, radius, outlineWidth, Color.fromRGB(43, 43, 43));

        float[] hsb = color.toHSB();
        float centreAngle = hsb[0] * 360f, centreOffset = hsb[1] * radius;
        double x = centreOffset * Math.cos(Math.toRadians(centreAngle));
        double y = centreOffset * Math.sin(Math.toRadians(centreAngle));
        x += centreX;
        y += centreY;

        renderer.outlineCircle((float) x, (float) y, 6, 1, Color.GRAY.withAlphaF(0.8f));
        renderer.outlineCircle((float) x, (float) y, 5, 1, Color.WHITE);

        for (int i = 0; i <= wheelSize; i += 1) {
            float factor = (float) i / wheelSize;
            renderer.fillRect(wheelSize + gap, i, sliderWidth, 1, Color.WHITE.darker(1 - factor));
        }
        renderer.outlineRect(wheelSize + gap, 0, sliderWidth, wheelSize, 1, Color.BLACK);

        float brightnessCursorY = ((1 - brightness) * wheelSize) - 5f;

        renderer.outlineRect(wheelSize + gap + 1, brightnessCursorY + 1, sliderWidth - 2, 10 - 2, 1, Color.WHITE);
        renderer.outlineRect(wheelSize + gap, brightnessCursorY, sliderWidth, 10, 1, Color.BLACK);

    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        if (event.isHeld(MouseButton.LEFT)) {
            update(event.x(), event.y());
        }

        return true;
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        update(event.x(), event.y());
        return true;
    }

    // Using the same math that generated the image to determine the color.
    // Also use this to update brightness.
    private void update(float x, float y) {
        if (y > wheelSize) {
            return;
        }

        if (x > wheelSize) {
            if (x >= wheelSize + gap && x <= wheelSize + gap + sliderWidth) {
                brightness = 1 - Math.clamp(y / wheelSize, 0, 1);
                this.colorChangeListener.callVoid((c) -> c.accept(color()));
            }

            return;
        }

        float centreX = wheelSize / 2.0f;
        float centreY = wheelSize / 2.0f;
        float radius = Math.min(centreX, centreY);

        double xOffset = x - centreX;
        double yOffset = y - centreY;
        double centreOffset = Math.hypot(xOffset, yOffset);
        if (centreOffset <= radius) {
            double centreAngle = (Math.toDegrees(Math.atan2(yOffset, xOffset)) + 360.0) % 360.0;
            float hue = (float) centreAngle / 360f;
            float saturation = (float) (centreOffset / radius);
            color = Color.fromHSB(hue, saturation, 1).withAlpha(color.getAlpha());

            this.colorChangeListener.callVoid((c) -> c.accept(color()));
        }
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(wheelSize + gap + sliderWidth, wheelSize + 10);
    }

    public void wheelSize(int wheelSize) {
        this.wheelSize = wheelSize;
        generate();
    }

    private void generate() {
        final int size = wheelSize - outlineWidth;
        final ByteImage2D image = new ByteImage2D(size, size, GL12C.GL_BGRA);
        image.clear();

        final float centreX = image.getWidth() / 2f, centreY = image.getHeight() / 2f;
        final float radius = Math.min(centreX, centreY);

        for (int x = 0; x < image.getWidth(); x++) { for (int y = 0; y < image.getHeight(); y++) {
            double xOffset = x - centreX;
            double yOffset = y - centreY;
            double centreOffset = Math.hypot(xOffset, yOffset);
            if (centreOffset <= radius) {
                double centreAngle = (Math.toDegrees(Math.atan2((yOffset), (xOffset))) + 360.0) % 360.0;
                float hue = (float) centreAngle / 360f ;
                float saturation = (float) (centreOffset / radius);
                Color color = Color.fromHSB(hue, saturation, 1);
                image.getPixels().putInt((y * image.getWidth() + x) * 4, color.toARGB());
            }
        }}

        generatedTexture = new ThinGLTexture(Texture2D.fromImage(image));
        image.free();
    }
}
