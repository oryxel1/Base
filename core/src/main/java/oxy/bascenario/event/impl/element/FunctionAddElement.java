package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.render.RenderLayer;
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
        final ElementRenderer<?> renderer = getRenderer(event.getElement(), event.getLayer());

        renderer.resize(0, 0); // TODO: Properly do this?
        screen.getElements().put(event.getId(), renderer);
    }

    // It's a bunch of if else yes, who cares anyway, not like it's a mess.
    public static ElementRenderer<?> getRenderer(Object element, RenderLayer layer) {
        final ElementRenderer<?> renderer;

        if (element instanceof Sprite sprite) {
            renderer = new SpriteRenderer(sprite, layer);
        } else if (element instanceof RendererImage image) {
            renderer = new ImageRenderer(image, layer);
        } else if (element instanceof Text text) {
            renderer = new TextRenderer(text, layer);
        } else if (element instanceof Rectangle rectangle) {
            renderer = new RectangleRenderer(rectangle, layer);
        } else if (element instanceof Circle circle) {
            renderer = new CircleRenderer(circle, layer);
        } else if (element instanceof Triangle triangle) {
            renderer = new TriangleRenderer(triangle, layer);
        } else if (element instanceof LocationInfo info) {
            renderer = new LocationInfoRenderer(info, layer);
        } else {
            throw new RuntimeException("Can't find the renderer for the element class type: " + element.getClass());
        }

        return renderer;
    }
}
