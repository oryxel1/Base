package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.*;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.thingl.*;
import oxy.bascenario.screens.renderer.element.SpriteRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.EmoticonMainRenderer;
import oxy.bascenario.screens.renderer.element.thingl.shape.CircleRenderer;
import oxy.bascenario.screens.renderer.element.thingl.shape.RectangleRenderer;
import oxy.bascenario.screens.renderer.element.thingl.shape.TriangleRenderer;

public class FunctionAddElement extends FunctionEvent<AddElementEvent> {
    public FunctionAddElement(AddElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = getRenderer(screen.getScenario(), event.element(), event.layer());

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
