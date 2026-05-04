package oxy.bascenario.serializers.types.event.impl.transition;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.event.ScreenTransitionEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class ScreenTransitionType implements TypeWithName<ScreenTransitionEvent> {
    @Override
    public String type() {
        return "screen-transition";
    }

    @Override
    public JsonElement write(ScreenTransitionEvent event) {
        final JsonObject object = new JsonObject();
        object.add("background", Types.NULLABLE_FILE_INFO_TYPE.write(event.background()));
        object.add("type", Types.TRANSITION_TYPE_TYPE.write(event.type()));
        object.addProperty("wait-duration", event.waitDuration());
        object.addProperty("out-duration", event.outDuration());
        object.addProperty("in-duration", event.inDuration());
        return object;
    }

    @Override
    public ScreenTransitionEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new ScreenTransitionEvent(
                Types.NULLABLE_FILE_INFO_TYPE.read(object.get("background")),
                Types.TRANSITION_TYPE_TYPE.read(object.get("type")),
                object.get("wait-duration").getAsInt(),
                object.get("out-duration").getAsInt(),
                object.get("in-duration").getAsInt()
        );
    }
}
