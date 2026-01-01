package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.dialogue.CloseOptionsEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class CloseOptionsType implements TypeWithName<CloseOptionsEvent> {
    @Override
    public String type() {
        return "close-options";
    }

    @Override
    public JsonElement write(CloseOptionsEvent event) {
        return JsonNull.INSTANCE;
    }

    @Override
    public CloseOptionsEvent read(JsonElement element) {
        return new CloseOptionsEvent();
    }

    @Override
    public void write(CloseOptionsEvent event, ByteBuf buf) {
    }

    @Override
    public CloseOptionsEvent read(ByteBuf buf) {
        return new CloseOptionsEvent();
    }
}
