package oxy.bascenario.serializers.types.event.impl.log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.log.AddLogEvent;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.element.ElementTypes;
import oxy.bascenario.serializers.types.primitive.EnumType;

import java.util.ArrayList;
import java.util.List;

public class AddLogType implements TypeWithName<AddLogEvent> {
    private static final EnumType<AddLogEvent.Type> TYPE_TYPE = new EnumType<>(AddLogEvent.Type.class, AddLogEvent.Type.values());

    @Override
    public String type() {
        return "add-log";
    }

    @Override
    public JsonElement write(AddLogEvent event) {
        final JsonObject object = new JsonObject();
        object.add("type", TYPE_TYPE.write(event.type()));
        object.addProperty("index", event.index());
        object.add("offset", ElementTypes.TEXT_OFFSET_TYPE.write(event.offset()));
        final JsonArray array = new JsonArray();
        for (Dialogue dialogue : event.dialogues()) {
            array.add(Types.DIALOGUE_TYPE.write(dialogue));
        }
        object.add("dialogues", array);

        return object;
    }

    @Override
    public AddLogEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<Dialogue> dialogues = new ArrayList<>();
        for (JsonElement dialogue : object.getAsJsonArray("dialogues")) {
            dialogues.add(Types.DIALOGUE_TYPE.read(dialogue));
        }
        return new AddLogEvent(
                TYPE_TYPE.read(object.get("type")), object.get("index").getAsInt(),
                ElementTypes.TEXT_OFFSET_TYPE.read(object.get("offset")), dialogues.toArray(new Dialogue[0])
        );
    }
}
