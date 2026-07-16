import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.RivetThinGLApplication;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Size;
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
        Container everything = new Container(BorderLayout.DEFAULT);
        everything.addChild(new SolidColor(Color.RED) {
            @Override
            public Size computeIdealSize(Size constraints) {
                return new Size(100, 20);
            }
        }.layoutOptions(BorderPosition.LEFT));
        everything.addChild(new Timeline().layoutOptions(BorderPosition.TOP));

        rivet.root().addChild(new ScrollContainer(everything, true, true));
    }

    private static class Timeline extends Component {
        @Override
        public void render(final Renderer renderer, final Size size) {
            renderer.fillRect(0, 0, size.width(), size.height(), Color.LIGHT_GRAY);
        }

        @Override
        public Size computeIdealSize(final Size constraints) {
            return new Size(0, 50);
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