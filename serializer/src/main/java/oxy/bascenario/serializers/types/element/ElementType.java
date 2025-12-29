package oxy.bascenario.serializers.types.element;

import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;

@SuppressWarnings("ALL")
public interface ElementType<T> extends Type<T> {
    String type();

    default JsonElement writeElement(Object o) {
        return write((T) o);
    }
    default void writeElement(Object t, ByteBuf buf) {
        write((T) t, buf);
    }
}
