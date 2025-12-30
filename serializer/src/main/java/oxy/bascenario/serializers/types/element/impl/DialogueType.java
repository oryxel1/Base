package oxy.bascenario.serializers.types.element.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.types.element.ElementTypes;

public class DialogueType implements Type<Dialogue> {
    @Override
    public JsonElement write(Dialogue dialogue) {
        final JsonObject object = new JsonObject();
        object.add("text", ElementTypes.TEXT_TYPE.write(dialogue.getDialogue()));
        object.addProperty("play-speed", dialogue.getPlaySpeed());
        return object;
    }

    @Override
    public Dialogue read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return Dialogue.builder().dialogue(ElementTypes.TEXT_TYPE.read(object.get("text"))).playSpeed(object.get("play-speed").getAsFloat()).build();
    }

    @Override
    public void write(Dialogue dialogue, ByteBuf buf) {
        ElementTypes.TEXT_TYPE.write(dialogue.getDialogue(), buf);
        buf.writeFloat(dialogue.getPlaySpeed());
    }

    @Override
    public Dialogue read(ByteBuf buf) {
        return Dialogue.builder().dialogue(ElementTypes.TEXT_TYPE.read(buf)).playSpeed(buf.readFloat()).build();
    }
}
