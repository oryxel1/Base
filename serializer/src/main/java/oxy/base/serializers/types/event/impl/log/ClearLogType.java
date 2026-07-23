package oxy.base.serializers.types.event.impl.log;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import oxy.base.api.event.element.ClearLogEvent;
import oxy.base.serializers.base.TypeWithName;

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
