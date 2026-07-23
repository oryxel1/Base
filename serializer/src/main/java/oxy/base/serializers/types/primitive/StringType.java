package oxy.base.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import oxy.base.serializers.base.Type;

public final class StringType implements Type<String> {
    @Override
    public JsonElement write(String s) {
        return new JsonPrimitive(s);
    }

    @Override
    public String read(JsonElement object) {
        return object.getAsString();
    }
}
