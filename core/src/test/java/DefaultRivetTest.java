import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.RivetThinGLApplication;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.FormattedLabel;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextFormat;
import net.lenni0451.rivet.text.model.TextOrigin;
import net.raphimc.thingl.resource.font.face.impl.FreeTypeFontFace;
import net.raphimc.thingl.resource.font.instance.FontInstance;
import net.raphimc.thingl.resource.font.instance.FontInstanceSet;
import net.raphimc.thingl.text.util.GlyphPredicate;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.SequencedMap;

public class DefaultRivetTest extends RivetThinGLApplication {

    public static void main(String[] args) {
        new DefaultRivetTest().run();
    }

    private static final int ONE_SECOND = 200;

    @Override
    protected void init(final Rivet rivet) {
        Container everything = new Container(BorderLayout.INSTANCE);
        everything.addChild(new Timeline().layoutOptions(BorderPosition.TOP));
        {
            Container elements = new Container(AbsoluteLayout.INSTANCE);
            int extra = 0;
            for (int i = 0; i < 20; i++) {
                elements.addChild(new TimelineElement(1).layoutOptions(new AbsoluteLayoutOptions(i * ONE_SECOND + extra, 0)));
                extra += ONE_SECOND;
            }
            everything.addChild(elements.layoutOptions(BorderPosition.CENTER));
        }
        rivet.root().addChild(new ScrollContainer(everything, true, true) {
            @Override
            public void render(Renderer renderer, Size size) {
                System.out.println(size);
                super.render(renderer, size);
            }

            @Override
            public Size computeIdealSize(Size constraints) {
                System.out.println(constraints);
                return super.computeIdealSize(constraints);
            }
        });
    }


    private static class Timeline extends Component {
        @Override
        public void render(final Renderer renderer, final Size size) {
            renderer.fillRect(0, 0, size.width(), size.height(), Color.LIGHT_GRAY);
            for (int i = 0; i < size.width() / ONE_SECOND; i++) {
                renderer.fillRect(i * ONE_SECOND, 0, 1, size.height(), Color.BLACK);
                renderer.text(this.rivet().backend().font().shapeText(String.valueOf(i), Color.RED), i * ONE_SECOND, size.height() / 2F, TextOrigin.Horizontal.VISUAL_CENTER, TextOrigin.Vertical.VISUAL_CENTER);
            }
        }

        @Override
        public Size computeIdealSize(final Size constraints) {
            return new Size(0, 50);
        }
    }

    private static class TimelineElement extends Component {
        private final int length;

        private TimelineElement(int length) {
            this.length = length;
        }

        @Override
        public void render(final Renderer renderer, final Size size) {
            renderer.fillRect(0, 0, size.width(), size.height(), Color.BLUE);
        }

        @Override
        public Size computeIdealSize(final Size constraints) {
            return new Size(ONE_SECOND * this.length, 50);
        }
    }

    static {
        if (System.getProperty("os.name").contains("Linux")) {
            GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_X11);
        }
    }

    public static FontInstanceSet createFont(final int size, final InputStream... streams) throws IOException {
        SequencedMap<FontInstance, GlyphPredicate> fonts = new LinkedHashMap<>();
        for (InputStream is : streams) {
            FontInstance font = new FreeTypeFontFace(is.readAllBytes()).getInstance(size);
            fonts.put(font, GlyphPredicate.all());
        }
        return new FontInstanceSet(fonts);
    }


    @Override
    protected FontInstanceSet createFont() throws Exception {
        return createFont(40, DefaultRivetTest.class.getResourceAsStream("assets/base/fonts/global/NotoSans-Regular.ttf"));
    }
}