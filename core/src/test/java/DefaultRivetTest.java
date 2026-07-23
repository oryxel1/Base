import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.GLFWApplication;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.container.tabcontainer.Tab;
import net.lenni0451.rivet.component.container.tabcontainer.TabAlignment;
import net.lenni0451.rivet.component.container.tabcontainer.TabContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridFill;
import net.lenni0451.rivet.layout.grid.GridLayout;
import net.lenni0451.rivet.layout.grid.GridOptions;
import net.lenni0451.rivet.math.Padding;
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

public class DefaultRivetTest extends GLFWApplication {

    public static void main(String[] args) {
        new DefaultRivetTest().run();
    }

    private static final int ONE_SECOND = 200;

    private int nextTabId = 1;

    @Override
    protected void init(final Rivet rivet) {
        TabContainer tabContainer = new TabContainer();
        tabContainer.tabAlignment().set(TabAlignment.CENTER);
        tabContainer.tabGap().set(5F);
        tabContainer.addLeftComponent(new Button("+", () -> {
            tabContainer.addTab(this.newTabHeader(tabContainer, "New Tab " + this.nextTabId), this.newTabContent());
            this.nextTabId++;
        }));
        tabContainer.addRightComponent(new Button("-", () -> {
            tabContainer.tabs().stream().filter(t -> t.headerBackground().active()).findFirst().ifPresent(tabContainer::removeTab);
        }));
        for (int i = 0; i < 2; i++) {
            tabContainer.addTab(this.newTabHeader(tabContainer, "Tab " + this.nextTabId), this.newTabContent());
            this.nextTabId++;
        }
        rivet.root().addChild(tabContainer);
    }

    private Component newTabHeader(final TabContainer tabContainer, final String name) {
        Container container = new Container(new GridLayout(10, 10));
        container.addChild(new Label(name).layoutOptions(new GridOptions(0, 0).withAnchor(GridAnchor.LEFT).withWeightX(1).withFill(GridFill.HORIZONTAL)));
        container.addChild(new Button(new Label("x").scale(0.75F).horizontalOrigin(TextOrigin.Horizontal.VISUAL_CENTER).verticalOrigin(TextOrigin.Vertical.VISUAL_CENTER), () -> {
            for (Tab tab : tabContainer.tabs()) {
                Label label = (Label) ((Container) tab.header()).children().get(0);
                if (label.text().equals(name)) {
                    tabContainer.removeTab(tab);
                    break;
                }
            }
        }), button -> {
            button.fixedSize(40, 40);
            button.layoutOptions(new GridOptions(1, 0).withAnchor(GridAnchor.RIGHT));
            button.innerPadding().set(Padding.EMPTY);
            button.cornerRadius().set(Float.MAX_VALUE);
            button.outlineWidth().set(0F);
            button.inactiveColor().set(Color.TRANSPARENT);
            button.activeColor().set(Color.RED.darker().darker().darker());
        });
        return container;
    }

    private Component newTabContent() {
        DecoratedContainer container = new DecoratedContainer(new SolidColor().outlineColor(Color.RED).outlineWidth(5F), new Label("Tab Content"));
        return container;
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