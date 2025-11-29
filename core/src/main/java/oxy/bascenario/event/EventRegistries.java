package oxy.bascenario.event;

import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.ColorOverlayEvent;
import oxy.bascenario.api.event.SetBackgroundEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.*;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.event.sound.SoundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.event.impl.*;
import oxy.bascenario.event.impl.animation.FunctionPlayAnimation;
import oxy.bascenario.event.impl.animation.FunctionSpriteAnimation;
import oxy.bascenario.event.impl.animation.FunctionStopAnimation;
import oxy.bascenario.event.impl.dialogue.FunctionAddDialogue;
import oxy.bascenario.event.impl.dialogue.FunctionCloseDialogue;
import oxy.bascenario.event.impl.dialogue.FunctionStartDialogue;
import oxy.bascenario.event.impl.element.*;
import oxy.bascenario.event.impl.FunctionColorOverlay;
import oxy.bascenario.event.impl.element.values.FunctionPositionElement;
import oxy.bascenario.event.impl.element.values.FunctionRotateElement;
import oxy.bascenario.event.impl.sound.FunctionPlaySoundEvent;
import oxy.bascenario.event.impl.sound.FunctionSoundEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventRegistries {
    public static Map<Class<? extends Event<?>>, Class<? extends FunctionEvent<?>>> EVENT_TO_FUNCTION = new HashMap<>();
    static {
        EVENT_TO_FUNCTION.put(SetBackgroundEvent.class, FunctionSetBackground.class);

        EVENT_TO_FUNCTION.put(ColorOverlayEvent.class, FunctionColorOverlay.class);
        EVENT_TO_FUNCTION.put(SoundEvent.class, FunctionSoundEvent.class);
        EVENT_TO_FUNCTION.put(PlaySoundEvent.class, FunctionPlaySoundEvent.class);

        EVENT_TO_FUNCTION.put(AddElementEvent.class, FunctionAddElement.class);
        EVENT_TO_FUNCTION.put(AttachElementEvent.class, FunctionAttachElement.class);
        EVENT_TO_FUNCTION.put(RemoveElementEvent.class, FunctionRemoveElement.class);
        EVENT_TO_FUNCTION.put(ElementIndexEvent.class, FunctionElementIndex.class);

        EVENT_TO_FUNCTION.put(PositionElementEvent.class, FunctionPositionElement.class);
        EVENT_TO_FUNCTION.put(RotateElementEvent.class, FunctionRotateElement.class);

        EVENT_TO_FUNCTION.put(ElementEffectEvent.class, FunctionElementEffect.class);

        EVENT_TO_FUNCTION.put(PlayAnimationEvent.class, FunctionPlayAnimation.class);
        EVENT_TO_FUNCTION.put(StopAnimationEvent.class, FunctionStopAnimation.class);

        EVENT_TO_FUNCTION.put(StartDialogueEvent.class, FunctionStartDialogue.class);
        EVENT_TO_FUNCTION.put(AddDialogueEvent.class, FunctionAddDialogue.class);
        EVENT_TO_FUNCTION.put(CloseDialogueEvent.class, FunctionCloseDialogue.class);

        EVENT_TO_FUNCTION.put(ShowOptionsEvent.class, FunctionShowOptions.class);

        EVENT_TO_FUNCTION.put(SpriteAnimationEvent.class, FunctionSpriteAnimation.class);

        EVENT_TO_FUNCTION = Collections.unmodifiableMap(EVENT_TO_FUNCTION);
    }
}
