package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.RedirectDialogueEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class RedirectDialogueType implements TypeWithName<RedirectDialogueEvent> {
    @Override
    public String type() {
        return "redirect-dialogue";
    }

    @Override
    public JsonElement write(RedirectDialogueEvent redirectDialogueEvent) {
        return new JsonPrimitive(redirectDialogueEvent.index());
    }

    @Override
    public RedirectDialogueEvent read(JsonElement element) {
        return new RedirectDialogueEvent(element.getAsInt());
    }

    @Override
    public void write(RedirectDialogueEvent redirectDialogueEvent, ByteBuf buf) {
        buf.writeInt(redirectDialogueEvent.index());
    }

    @Override
    public RedirectDialogueEvent read(ByteBuf buf) {
        return new RedirectDialogueEvent(buf.readInt());
    }
}
