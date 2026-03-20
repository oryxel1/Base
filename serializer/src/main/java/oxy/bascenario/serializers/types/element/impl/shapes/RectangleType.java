package oxy.bascenario.serializers.types.element.impl.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class RectangleType implements TypeWithName<Rectangle> {
    @Override
    public String type() {
        return "rectangle";
    }

    @Override
    public JsonElement write(Rectangle rectangle) {
        final JsonObject object = new JsonObject();
        final JsonArray array = new JsonArray();
        array.add(rectangle.width());
        array.add(rectangle.height());

        object.add("size", array);
        object.add("color", Types.COLOR_TYPE.write(rectangle.color()));
        object.addProperty("outline-only", rectangle.outlineOnly());
        return object;
    }

    @Override
    public Rectangle read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final JsonArray array = object.getAsJsonArray("size");
        return new Rectangle(array.get(0).getAsInt(), array.get(1).getAsInt(), Types.COLOR_TYPE.read(object.get("color")), object.get("outline-only").getAsBoolean());
    }
}
