package oxy.bascenario.serializers.types.event.impl.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.utility.CheckForDialogueEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

import java.util.ArrayList;
import java.util.List;

public class CheckForDialogueType implements TypeWithName<CheckForDialogueEvent> {
    @Override
    public String type() {
        return "check-for-dialogue";
    }

    @Override
    public JsonElement write(CheckForDialogueEvent checkForDialogueEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("index", checkForDialogueEvent.index());

        final JsonArray array = new JsonArray();
        for (Event event : checkForDialogueEvent.events()) {
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
    public CheckForDialogueEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<Event> events = new ArrayList<>();
        for (JsonElement event : object.getAsJsonArray("events")) {
            events.add(Types.EVENT_TYPE.read(event));
        }

        return new CheckForDialogueEvent(object.get("index").getAsInt(), events.toArray(new Event[0]));
    }
}
