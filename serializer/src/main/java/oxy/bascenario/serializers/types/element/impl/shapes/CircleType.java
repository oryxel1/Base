package oxy.bascenario.serializers.types.element.impl.shapes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class CircleType implements TypeWithName<Circle> {
    @Override
    public String type() {
        return "circle";
    }

    @Override
    public JsonElement write(Circle circle) {
        final JsonObject object = new JsonObject();
        object.addProperty("radius", circle.radius());
        object.add("color", Types.COLOR_TYPE.write(circle.color()));
        object.addProperty("outline-only", circle.outlineOnly());
        return object;
    }

    @Override
    public Circle read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new Circle(object.get("radius").getAsInt(), Types.COLOR_TYPE.read(object.get("color")), object.get("outline-only").getAsBoolean());
    }
}
