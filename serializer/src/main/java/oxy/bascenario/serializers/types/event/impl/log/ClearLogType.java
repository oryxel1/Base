package oxy.bascenario.serializers.types.event.impl.log;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import oxy.bascenario.api.event.element.ClearLogEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class ClearLogType implements TypeWithName<ClearLogEvent> {
    @Override
    public String type() {
        return "clear-log";
    }

    @Override
    public JsonElement write(ClearLogEvent o) {
        return JsonNull.INSTANCE;
    }

    @Override
    public ClearLogEvent read(JsonElement element) {
        return new ClearLogEvent();
    }
}
