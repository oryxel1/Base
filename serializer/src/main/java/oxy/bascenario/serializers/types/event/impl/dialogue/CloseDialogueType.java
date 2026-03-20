package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class CloseDialogueType implements TypeWithName<CloseDialogueEvent> {
    @Override
    public String type() {
        return "close-dialogue";
    }

    @Override
    public JsonElement write(CloseDialogueEvent closeDialogueEvent) {
        return JsonNull.INSTANCE;
    }

    @Override
    public CloseDialogueEvent read(JsonElement element) {
        return new CloseDialogueEvent();
    }
}
