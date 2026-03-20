package oxy.bascenario.serializers.base;

import com.google.gson.JsonElement;


@SuppressWarnings("ALL")
public interface TypeWithName<T> extends Type<T> {
    String type();

    default JsonElement writeElement(Object o) {
        return write((T) o);
    }
}
