package oxy.base.serializers.types.scenario;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.Timestamp;
import oxy.base.api.event.api.Event;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.Type;

import java.util.ArrayList;
import java.util.List;

public class TimestampType implements Type<Timestamp> {
    @Override
    public JsonElement write(Timestamp timestamp) {
        final JsonObject object = new JsonObject();
        object.add("dialogue-index", Types.NULLABLE_INT.write(timestamp.dialogueIndex()));
        object.addProperty("wait-for-dialogue", timestamp.waitForDialogue());
        object.addProperty("time", timestamp.time());

        final JsonArray array = new JsonArray();
        for (Event event : timestamp.events()) {
            JsonElement element = null;
            try {
                element = Types.EVENT_TYPE.write(event);
            } catch (Exception ignored) {
            }
            if (element != null) {
                array.add(element);
            }
        }
        object.add("events", array);
        return object;
    }

    @Override
    public Timestamp read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        Integer dialogueIndex = null;
        if (object.has("dialogue-index")) {
            dialogueIndex = Types.NULLABLE_INT.read(object.get("dialogue-index"));
        }

        final List<Event> events = new ArrayList<>();
        for (JsonElement event : object.getAsJsonArray("events")) {
            events.add(Types.EVENT_TYPE.read(event));
        }

        return new Timestamp(object.get("wait-for-dialogue").getAsBoolean(), dialogueIndex, object.get("time").getAsInt(), events);
    }
}
