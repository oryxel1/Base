package oxy.bascenario.event;

import oxy.bascenario.api.event.Event;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.event.impl.FunctionColorOverlay;
import oxy.bascenario.event.impl.FunctionSetBackground;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventMapper {
    public static Map<Class<? extends Event<?>>, Class<? extends EventFunction<?>>> EVENT_TO_FUNCTION = new HashMap<>();
    static {
        EVENT_TO_FUNCTION.put(SetBackgroundEvent.class, FunctionSetBackground.class);
        EVENT_TO_FUNCTION.put(ColorOverlayEvent.class, FunctionColorOverlay.class);
        EVENT_TO_FUNCTION = Collections.unmodifiableMap(EVENT_TO_FUNCTION);
    }
}
