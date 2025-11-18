package oxy.bascenario.event;

import oxy.bascenario.api.event.Event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
    Class<? extends Event> value();
}
