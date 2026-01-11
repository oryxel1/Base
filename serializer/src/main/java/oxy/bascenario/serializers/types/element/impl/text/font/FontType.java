package oxy.bascenario.serializers.types.element.impl.text.font;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.text.font.Font;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.types.element.ElementTypes;

import java.util.Optional;

public class FontType implements Type<Font> {
    @Override
    public JsonElement write(Font font) {
        if (font.file() != null) {
            return Types.FILE_INFO_TYPE.write(font.file());
        }

        final JsonObject object = new JsonObject();
        object.add("style", ElementTypes.FONT_STYLE_TYPE.write(font.style()));
        object.add("type", ElementTypes.FONT_TYPE_TYPE.write(font.type()));
        return object;
    }

    @Override
    public Font read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        if (!object.has("style")) {
            return new Font(Types.FILE_INFO_TYPE.read(object), null, null);
        }

        return new Font(null, ElementTypes.FONT_STYLE_TYPE.read(object.get("style")), ElementTypes.FONT_TYPE_TYPE.read(object.get("type")));
    }

    @Override
    public void write(Font font, ByteBuf buf) {
        buf.writeBoolean(font.file() != null);
        if (font.file() != null) {
            Types.FILE_INFO_TYPE.write(font.file(), buf);
            return;
        }

        ElementTypes.FONT_STYLE_TYPE.write(font.style(), buf);
        ElementTypes.FONT_TYPE_TYPE.write(font.type(), buf);
    }

    @Override
    public Font read(ByteBuf buf) {
        if (buf.readBoolean()) {
            return new Font(Types.FILE_INFO_TYPE.read(buf), null, null);
        }

        return new Font(null, ElementTypes.FONT_STYLE_TYPE.read(buf), ElementTypes.FONT_TYPE_TYPE.read(buf));
    }
}
