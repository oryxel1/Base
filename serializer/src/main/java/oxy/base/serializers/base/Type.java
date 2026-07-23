package oxy.base.serializers.base;

import com.google.gson.JsonElement;

public interface Type<T> {
    JsonElement write(T t);
    T read(JsonElement element);
}
