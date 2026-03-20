package oxy.bascenario.serializers.types.scenario;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.Type;

import java.util.ArrayList;
import java.util.List;

public class ScenarioType implements Type<Scenario> {
    @Override
    public JsonElement write(Scenario scenario) {
        final JsonObject object = new JsonObject();
        object.addProperty("name", scenario.getName());
        final JsonArray array = new JsonArray();
        scenario.getTimestamps().forEach(timestamp -> array.add(Types.TIMESTAMP_TYPE.write(timestamp)));
        object.add("timestamps", array);
        return object;
    }

    @Override
    public Scenario read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<Timestamp> timestamps = new ArrayList<>();
        for (JsonElement timestamp : object.getAsJsonArray("timestamps")) {
            timestamps.add(Types.TIMESTAMP_TYPE.read(timestamp));
        }

        return new Scenario(object.get("name").getAsString(), timestamps);
    }
}
