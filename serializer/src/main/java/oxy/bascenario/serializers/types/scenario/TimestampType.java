package oxy.bascenario.serializers.types.scenario;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.Type;

import java.util.ArrayList;
import java.util.List;

public class TimestampType implements Type<Timestamp> {
    @Override
    public JsonElement write(Timestamp timestamp) {
        final JsonObject object = new JsonObject();
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
        final List<Event> events = new ArrayList<>();
        for (JsonElement event : object.getAsJsonArray("events")) {
            events.add(Types.EVENT_TYPE.read(event));
        }

        return new Timestamp(object.get("wait-for-dialogue").getAsBoolean(), object.get("time").getAsInt(), events);
    }
}
