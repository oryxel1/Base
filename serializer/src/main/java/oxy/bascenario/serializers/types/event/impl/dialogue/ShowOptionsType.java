package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonArray;
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
        final JsonObject options = new JsonObject();
        event.options().forEach(options::addProperty);
        return options;
    }

    @Override
    public ShowOptionsEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final Map<String, Integer> map = new LinkedHashMap<>();

        for (String key : object.keySet()) {
            map.put(key, object.get(key).getAsInt());
        }

        return new ShowOptionsEvent(map);
    }
}
