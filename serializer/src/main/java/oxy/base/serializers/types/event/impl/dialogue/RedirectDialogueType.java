package oxy.base.serializers.types.event.impl.dialogue;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import oxy.base.api.event.dialogue.RedirectDialogueEvent;
import oxy.base.serializers.base.TypeWithName;

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
}
