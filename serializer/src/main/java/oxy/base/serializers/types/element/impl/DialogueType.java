package oxy.base.serializers.types.element.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.render.elements.Dialogue;
import oxy.base.serializers.base.Type;
import oxy.base.serializers.types.element.ElementTypes;

public class DialogueType implements Type<Dialogue> {
    @Override
    public JsonElement write(Dialogue dialogue) {
        final JsonObject object = new JsonObject();
        object.add("text", ElementTypes.TEXT_TYPE.write(dialogue.getDialogue()));
        object.addProperty("play-speed", dialogue.getPlaySpeed());
        object.addProperty("text-offset", dialogue.getOffset());
        return object;
    }

    @Override
    public Dialogue read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return Dialogue.builder().dialogue(ElementTypes.TEXT_TYPE.read(object.get("text")))
                .playSpeed(object.get("play-speed").getAsFloat()).offset(object.get("text-offset").getAsFloat()).build();
    }
}
