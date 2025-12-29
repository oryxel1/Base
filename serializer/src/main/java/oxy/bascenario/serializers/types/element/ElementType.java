package oxy.bascenario.serializers.types.element;

import oxy.bascenario.serializers.Type;

public interface ElementType<T> extends Type<T> {
    String type();
}
