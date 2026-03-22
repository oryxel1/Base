package oxy.bascenario.serializers.types.element.impl.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class TriangleType implements TypeWithName<Triangle> {
    @Override
    public JsonElement write(Triangle triangle) {
        final JsonArray array = new JsonArray();
        array.add(triangle.x1());
        array.add(triangle.y1());
        array.add(triangle.x2());
        array.add(triangle.y2());
        array.add(triangle.x3());
        array.add(triangle.y3());
        final JsonObject object = new JsonObject();
        object.add("triangle", array);
        object.add("color", Types.COLOR_TYPE.write(triangle.color()));
        return object;
    }

    @Override
    public Triangle read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final JsonArray array = object.getAsJsonArray("triangle");
        return new Triangle(
                array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat(), array.get(3).getAsFloat(), array.get(4).getAsFloat(), array.get(5).getAsFloat(),
                Types.COLOR_TYPE.read(object.get("color"))
        );
    }

    @Override
    public String type() {
        return "triangle";
    }
}
