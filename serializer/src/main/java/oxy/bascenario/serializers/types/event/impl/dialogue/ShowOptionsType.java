package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
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

        final JsonObject options = new JsonObject();
        event.options().forEach(options::addProperty);
        object.add("options", options);
        return object;
    }

    @Override
    public ShowOptionsEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final Map<String, Integer> map = new LinkedHashMap<>();

        if (!object.has("options")) {
            for (String key : object.keySet()) {
                map.put(key, object.get(key).getAsInt());
            }
            return new ShowOptionsEvent(map);
        }

        final JsonObject o = object.getAsJsonObject("options");
        for (String key : o.keySet()) {
            map.put(key, o.get(key).getAsInt());
        }

        return new ShowOptionsEvent(ElementTypes.FONT_TYPE_TYPE.read(object.get("font")), map);
    }
}