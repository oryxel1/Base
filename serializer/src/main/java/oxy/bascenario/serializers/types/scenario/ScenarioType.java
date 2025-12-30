package oxy.bascenario.serializers.types.scenario;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
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

        return new Scenario(object.get("name").getAsString(), Scenario.SaveType.JSON, timestamps);
    }

    @Override
    public void write(Scenario scenario, ByteBuf buf) {
        Types.STRING_TYPE.write(scenario.getName(), buf);

        buf.writeInt(scenario.getTimestamps().size());
        scenario.getTimestamps().forEach(timestamp -> Types.TIMESTAMP_TYPE.write(timestamp, buf));
    }

    @Override
    public Scenario read(ByteBuf buf) {
        final String name = Types.STRING_TYPE.read(buf);
        final List<Timestamp> timestamps = new ArrayList<>();
        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            timestamps.add(Types.TIMESTAMP_TYPE.read(buf));
        }

        return new Scenario(name, Scenario.SaveType.BINARY, timestamps);
    }
}
