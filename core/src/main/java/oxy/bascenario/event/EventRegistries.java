package oxy.bascenario.event;

import oxy.bascenario.api.event.Event;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.api.event.impl.SpriteAnimationEvent;
import oxy.bascenario.api.event.impl.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.impl.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.impl.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.impl.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.impl.element.*;
import oxy.bascenario.api.event.impl.sound.PlaySoundEvent;
import oxy.bascenario.api.event.impl.sound.SoundEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.event.impl.*;
import oxy.bascenario.event.impl.dialogue.FunctionAddDialogue;
import oxy.bascenario.event.impl.dialogue.FunctionCloseDialogue;
import oxy.bascenario.event.impl.dialogue.FunctionStartDialogue;
import oxy.bascenario.event.impl.element.*;
import oxy.bascenario.event.impl.FunctionColorOverlay;
import oxy.bascenario.event.impl.sound.FunctionPlaySoundEvent;
import oxy.bascenario.event.impl.sound.FunctionSoundEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventRegistries {
    public static Map<Class<? extends Event<?>>, Class<? extends EventFunction<?>>> EVENT_TO_FUNCTION = new HashMap<>();
    static {
        EVENT_TO_FUNCTION.put(SetBackgroundEvent.class, FunctionSetBackground.class);

        EVENT_TO_FUNCTION.put(ColorOverlayEvent.class, FunctionColorOverlay.class);
        EVENT_TO_FUNCTION.put(SoundEvent.class, FunctionSoundEvent.class);
        EVENT_TO_FUNCTION.put(PlaySoundEvent.class, FunctionPlaySoundEvent.class);

        EVENT_TO_FUNCTION.put(AddElementEvent.class, FunctionAddElement.class);
        EVENT_TO_FUNCTION.put(RemoveElementEvent.class, FunctionRemoveElement.class);
        EVENT_TO_FUNCTION.put(MoveElementEvent.class, FunctionMoveElement.class);
        EVENT_TO_FUNCTION.put(ElementIndexEvent.class, FunctionElementIndex.class);
        EVENT_TO_FUNCTION.put(ScaleElementEvent.class, FunctionScaleElement.class);
        EVENT_TO_FUNCTION.put(ElementEffectEvent.class, FunctionElementEffect.class);

        EVENT_TO_FUNCTION.put(StartDialogueEvent.class, FunctionStartDialogue.class);
        EVENT_TO_FUNCTION.put(AddDialogueEvent.class, FunctionAddDialogue.class);
        EVENT_TO_FUNCTION.put(CloseDialogueEvent.class, FunctionCloseDialogue.class);

        EVENT_TO_FUNCTION.put(ShowOptionsEvent.class, FunctionShowOptions.class);

        EVENT_TO_FUNCTION.put(SpriteAnimationEvent.class, FunctionSpriteAnimation.class);

        EVENT_TO_FUNCTION = Collections.unmodifiableMap(EVENT_TO_FUNCTION);
    }
}
