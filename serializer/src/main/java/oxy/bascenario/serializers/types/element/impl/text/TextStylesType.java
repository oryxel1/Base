package oxy.bascenario.serializers.types.element.impl.text;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.serializers.Type;

import java.util.EnumSet;
import java.util.Set;

public class TextStylesType implements Type<Set<TextStyle>> {
    @Override
    public JsonElement write(Set<TextStyle> textStyles) {
        final JsonArray array = new JsonArray();
        for (TextStyle style : textStyles) {
            array.add(style.name());
        }

        return array;
    }

    @Override
    public Set<TextStyle> read(JsonElement element) {
        final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);
        final JsonArray array = element.getAsJsonArray();
        for (JsonElement element1 : array) {
            styles.add(TextStyle.valueOf(element1.getAsString()));
        }

        return styles;
    }

    @Override
    public void write(Set<TextStyle> textStyles, ByteBuf buf) {
        int flags = 0;
        for (TextStyle style : textStyles) {
            flags |= style.getValue();
        }
        buf.writeByte(flags);
    }

    @Override
    public Set<TextStyle> read(ByteBuf buf) {
        int flags = buf.readUnsignedByte();

        final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);
        for (TextStyle style : TextStyle.values()) {
            if ((flags & style.getValue()) == 0) {
                continue;
            }

            styles.add(style);
        }

        return styles;
    }
}
