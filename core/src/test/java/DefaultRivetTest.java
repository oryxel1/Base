import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.RivetThinGLApplication;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
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

    @Override
    protected void init(final Rivet rivet) {
        Container container = new Container(AnchorLayout.INSTANCE);

        final Container innerContainer = new Container(AnchorLayout.INSTANCE) {
            @Override
            public void render(Renderer renderer, Size size) {
                renderer.fillRect(0, 0, size.width(), size.height(), Color.WHITE);
                super.render(renderer, size);
            }
        };
        container.addChild(innerContainer, c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.1F).withAnchorMinY(0.1F).withAnchorMaxX(0.8f).withAnchorMaxY(0.6f));
        });

        Container innerInnerContainer = new Container(new VerticalListLayout(3, false));
        innerContainer.addChild(new ScrollContainer(innerInnerContainer));

        for (int i = 0; i < 100; i++) {
            innerInnerContainer.addChild(new Button("BUTTON " + i, () -> {}));
        }

        rivet.root().addChild(container);
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