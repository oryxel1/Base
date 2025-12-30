package oxy.bascenario.serializers.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.serializers.base.Type;

public class ColorType implements Type<Color> {
    @Override
    public JsonElement write(Color color) {
        final JsonArray array = new JsonArray();
        array.add(color.getRed());
        array.add(color.getGreen());
        array.add(color.getBlue());
        array.add(color.getAlpha());
        return array;
    }

    @Override
    public Color read(JsonElement element) {
        final JsonArray array = element.getAsJsonArray();
        return Color.fromRGBA(array.get(0).getAsInt(), array.get(1).getAsInt(), array.get(2).getAsInt(), array.get(3).getAsInt());
    }

    @Override
    public void write(Color color, ByteBuf buf) {
        buf.writeInt(color.toRGBA());
    }

    @Override
    public Color read(ByteBuf buf) {
        return Color.fromRGBA(buf.readInt());
    }
}
