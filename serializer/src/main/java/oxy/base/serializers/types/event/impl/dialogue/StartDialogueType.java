package oxy.base.serializers.types.event.impl.dialogue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.dialogue.StartDialogueEvent;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.TypeWithName;
import oxy.base.serializers.types.element.ElementTypes;

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
        object.add("offset", ElementTypes.TEXT_OFFSET_TYPE.write(event.offset()));
        object.add("font", ElementTypes.FONT_TYPE_TYPE.write(event.type()));
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
        return new StartDialogueEvent(
                ElementTypes.TEXT_OFFSET_TYPE.read(object.get("offset")),
                ElementTypes.FONT_TYPE_TYPE.read(object.get("font")), object.get("index").getAsInt(), object.get("name").getAsString(), object.get("association").getAsString(),
                object.get("background").getAsBoolean(), dialogues.toArray(new Dialogue[0])
        );
    }
}
