package oxy.bascenario.serializers.types.element.impl.shapes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.types.element.ElementType;

public class TriangleType implements ElementType<Triangle> {
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
    public void write(Triangle triangle, ByteBuf buf) {
        buf.writeFloat(triangle.x1());
        buf.writeFloat(triangle.y1());
        buf.writeFloat(triangle.x2());
        buf.writeFloat(triangle.y2());
        buf.writeFloat(triangle.x3());
        buf.writeFloat(triangle.y3());
        Types.COLOR_TYPE.write(triangle.color(), buf);
    }

    @Override
    public Triangle read(ByteBuf buf) {
        return new Triangle(
                buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(),
                Types.COLOR_TYPE.read(buf)
        );
    }

    @Override
    public String type() {
        return "triangle";
    }
}
