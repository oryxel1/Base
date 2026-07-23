package oxy.base.event.impl.element;

import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.base.api.Scenario;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.LocationInfo;
import oxy.base.api.render.elements.Preview;
import oxy.base.api.render.elements.Sprite;
import oxy.base.api.render.elements.emoticon.Emoticon;
import oxy.base.api.render.elements.image.AnimatedImage;
import oxy.base.api.render.elements.image.Image;
import oxy.base.api.render.elements.shape.Circle;
import oxy.base.api.render.elements.shape.Rectangle;
import oxy.base.api.render.elements.shape.Triangle;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.render.elements.text.AnimatedText;
import oxy.base.api.render.elements.text.Text;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.thingl.*;
import oxy.base.screens.renderer.element.SpriteRenderer;
import oxy.base.screens.renderer.element.base.ElementRenderer;
import oxy.base.screens.renderer.element.thingl.emoticon.EmoticonMainRenderer;
import oxy.base.screens.renderer.element.thingl.shape.CircleRenderer;
import oxy.base.screens.renderer.element.thingl.shape.RectangleRenderer;
import oxy.base.screens.renderer.element.thingl.shape.TriangleRenderer;
import oxy.base.screens.renderer.element.thingl.text.AnimatedTextRenderer;
import oxy.base.screens.renderer.element.thingl.text.TextRenderer;

public class FunctionAddElement extends FunctionEvent<AddElementEvent> {
    public FunctionAddElement(AddElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = getRenderer(screen.getScenario(), event.element(), event.layer());
        renderer.getPosition().set(EasingFunction.LINEAR, event.position(), 0);

        renderer.resize(0, 0); // TODO: Properly do this?
        screen.getElements().put(Math.abs(event.id()), renderer);
    }

    // It's a bunch of switch case yes, who cares anyway, not like it's a mess.
    public static ElementRenderer<?> getRenderer(Scenario scenario, Object element, RenderLayer layer) {
        return switch (element) {
            case Sprite sprite -> new SpriteRenderer(sprite, layer, scenario);
            case AnimatedImage image -> new AnimatedImageRenderer(scenario, image, layer);
            case Image image -> new ImageRenderer(image, layer, scenario);
            case Text text -> new TextRenderer(text, layer, scenario);
            case AnimatedText text -> new AnimatedTextRenderer(text, layer, scenario);
            case Rectangle rectangle -> new RectangleRenderer(rectangle, layer);
            case Circle circle -> new CircleRenderer(circle, layer);
            case Triangle triangle -> new TriangleRenderer(triangle, layer);
            case LocationInfo info -> new LocationInfoRenderer(info, layer);
            case Emoticon emoticon -> new EmoticonMainRenderer(emoticon, layer);
            case Preview preview -> new PreviewRenderer(preview, layer, scenario);
            default -> throw new RuntimeException("Can't find the renderer for the element class type: " + element.getClass());
        };
    }
}
