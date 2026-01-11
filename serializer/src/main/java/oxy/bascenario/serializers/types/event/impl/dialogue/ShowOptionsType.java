package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.element.ElementTypes;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShowOptionsType implements TypeWithName<ShowOptionsEvent> {
    @Override
    public String type() {
        return "show-options";
    }

    @Override
    public JsonElement write(ShowOptionsEvent event) {
        final JsonObject object = new JsonObject();
        object.add("font", ElementTypes.FONT_TYPE_TYPE.write(event.type()));
        event.options().forEach(object::addProperty);
        return object;
    }

    @Override
    public ShowOptionsEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final Map<String, Integer> map = new LinkedHashMap<>();
        for (String key : object.keySet()) {
            map.put(key, object.get(key).getAsInt());
        }

        return new ShowOptionsEvent(ElementTypes.FONT_TYPE_TYPE.read(object.get("font")), map);
    }

    @Override
    public void write(ShowOptionsEvent event, ByteBuf buf) {
        ElementTypes.FONT_TYPE_TYPE.write(event.type(), buf);
        buf.writeInt(event.options().size());
        event.options().forEach((k, v) -> {
            Types.STRING_TYPE.write(k, buf);
            buf.writeInt(v);
        });
    }

    @Override
    public ShowOptionsEvent read(ByteBuf buf) {
        FontType type = ElementTypes.FONT_TYPE_TYPE.read(buf);
        final Map<String, Integer> map = new LinkedHashMap<>();
        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            map.put(Types.STRING_TYPE.read(buf), buf.readInt());
        }
        return new ShowOptionsEvent(type, map);
    }
}
