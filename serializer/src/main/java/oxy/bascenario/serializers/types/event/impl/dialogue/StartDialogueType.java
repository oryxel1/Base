package oxy.bascenario.serializers.types.event.impl.dialogue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

import java.util.ArrayList;
import java.util.List;

public class StartDialogueType implements TypeWithName<StartDialogueEvent> {
    @Override
    public String type() {
        return "start-dialogue";
    }

    @Override
    public JsonElement write(StartDialogueEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("index", event.index());
        object.addProperty("name", event.name());
        object.addProperty("association", event.association());
        object.addProperty("background", event.background());
        final JsonArray array = new JsonArray();
        for (Dialogue dialogue : event.dialogues()) {
            array.add(Types.DIALOGUE_TYPE.write(dialogue));
        }
        object.add("dialogues", array);

        return object;
    }

    @Override
    public StartDialogueEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<Dialogue> dialogues = new ArrayList<>();
        for (JsonElement dialogue : object.getAsJsonArray("dialogues")) {
            dialogues.add(Types.DIALOGUE_TYPE.read(dialogue));
        }
        return new StartDialogueEvent(object.get("index").getAsInt(), object.get("name").getAsString(), object.get("association").getAsString(),
                object.get("background").getAsBoolean(), dialogues.toArray(new Dialogue[0])
        );
    }

    @Override
    public void write(StartDialogueEvent event, ByteBuf buf) {
        buf.writeInt(event.index());
        Types.STRING_TYPE.write(event.name(), buf);
        Types.STRING_TYPE.write(event.association(), buf);
        buf.writeBoolean(event.background());

        buf.writeInt(event.dialogues().length);
        for (Dialogue dialogue : event.dialogues()) {
            Types.DIALOGUE_TYPE.write(dialogue, buf);
        }
    }

    @Override
    public StartDialogueEvent read(ByteBuf buf) {
        int index = buf.readInt();
        String name = Types.STRING_TYPE.read(buf), association = Types.STRING_TYPE.read(buf);
        boolean background = buf.readBoolean();

        final List<Dialogue> dialogues = new ArrayList<>();
        for (int i = 0; i < buf.readInt(); i++) {
            dialogues.add(Types.DIALOGUE_TYPE.read(buf));
        }

        return new StartDialogueEvent(index, name, association, background, dialogues.toArray(new Dialogue[0]));
    }
}
