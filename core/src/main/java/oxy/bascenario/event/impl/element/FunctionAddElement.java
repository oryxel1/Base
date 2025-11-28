package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.thingl.ImageRenderer;
import oxy.bascenario.screens.renderer.element.SpriteRenderer;
import oxy.bascenario.screens.renderer.element.thingl.LocationInfoRenderer;
import oxy.bascenario.screens.renderer.element.thingl.TextRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.screens.renderer.element.thingl.shape.CircleRenderer;
import oxy.bascenario.screens.renderer.element.thingl.shape.RectangleRenderer;
import oxy.bascenario.screens.renderer.element.thingl.shape.TriangleRenderer;

public class FunctionAddElement extends FunctionEvent<AddElementEvent> {
    public FunctionAddElement(AddElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        // It's a bunch of if else yes, who cares anyway, not like it's a mess.
        final ElementRenderer<?> renderer;
        final Object element = event.getElement();
        if (element instanceof Sprite sprite) {
            renderer = new SpriteRenderer(sprite, event.getLayer());
        } else if (element instanceof RendererImage image) {
            renderer = new ImageRenderer(image, event.getLayer());
        } else if (element instanceof Text text) {
            renderer = new TextRenderer(text, event.getLayer());
        } else if (element instanceof Rectangle rectangle) {
            renderer = new RectangleRenderer(rectangle, event.getLayer());
        } else if (element instanceof Circle circle) {
            renderer = new CircleRenderer(circle, event.getLayer());
        } else if (element instanceof Triangle triangle) {
            renderer = new TriangleRenderer(triangle, event.getLayer());
        } else if (element instanceof LocationInfo info) {
            renderer = new LocationInfoRenderer(info, event.getLayer());
        } else {
            throw new RuntimeException("Can't find the renderer for the element class type: " + event.getElement().getClass());
        }

        renderer.resize(0, 0); // TODO: Properly do this?
        screen.getElements().put(event.getId(), renderer);
    }
}
