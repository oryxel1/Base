package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

import java.util.ArrayList;
import java.util.List;

public class AddDialogueType implements TypeWithName<AddDialogueEvent> {
    @Override
    public String type() {
        return "add-dialogue";
    }

    @Override
    public JsonElement write(AddDialogueEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("index", event.index());
        final JsonArray array = new JsonArray();
        for (Dialogue dialogue : event.dialogues()) {
            array.add(Types.DIALOGUE_TYPE.write(dialogue));
        }
        object.add("dialogues", array);

        return object;
    }

    @Override
    public AddDialogueEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<Dialogue> dialogues = new ArrayList<>();
        for (JsonElement dialogue : object.getAsJsonArray("dialogues")) {
            dialogues.add(Types.DIALOGUE_TYPE.read(dialogue));
        }
        return new AddDialogueEvent(object.get("index").getAsInt(), dialogues.toArray(new Dialogue[0]));
    }

    @Override
    public void write(AddDialogueEvent event, ByteBuf buf) {
        buf.writeInt(event.index());
        buf.writeInt(event.dialogues().length);
        for (Dialogue dialogue : event.dialogues()) {
            Types.DIALOGUE_TYPE.write(dialogue, buf);
        }
    }

    @Override
    public AddDialogueEvent read(ByteBuf buf) {
        int index = buf.readInt();
        final List<Dialogue> dialogues = new ArrayList<>();
        for (int i = 0; i < buf.readInt(); i++) {
            dialogues.add(Types.DIALOGUE_TYPE.read(buf));
        }

        return new AddDialogueEvent(index, dialogues.toArray(new Dialogue[0]));
    }
}
