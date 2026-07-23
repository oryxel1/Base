package oxy.base.event;

import oxy.base.api.event.*;
import oxy.base.api.event.background.ClearBackgroundEvent;
import oxy.base.api.event.animation.PlayAnimationEvent;
import oxy.base.api.event.animation.StopAnimationEvent;
import oxy.base.api.event.api.Event;
import oxy.base.api.event.color.ColorOverlayEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.event.animation.SpriteAnimationEvent;
import oxy.base.api.event.color.SetColorEvent;
import oxy.base.api.event.dialogue.*;
import oxy.base.api.event.element.*;
import oxy.base.api.event.element.focus.FocusElementEvent;
import oxy.base.api.event.element.focus.UnfocusElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.event.element.values.RotateElementEvent;
import oxy.base.api.event.log.AddLogEvent;
import oxy.base.api.event.sound.PlaySoundEvent;
import oxy.base.api.event.sound.SoundEvent;
import oxy.base.api.event.sound.SoundVolumeEvent;
import oxy.base.api.event.sound.StopSoundEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.event.impl.*;
import oxy.base.event.impl.animation.FunctionPlayAnimation;
import oxy.base.event.impl.animation.FunctionSpriteAnimation;
import oxy.base.event.impl.animation.FunctionStopAnimation;
import oxy.base.event.impl.background.FunctionClearBackground;
import oxy.base.event.impl.background.FunctionSetBackground;
import oxy.base.event.impl.color.FunctionSetColor;
import oxy.base.event.impl.dialogue.*;
import oxy.base.event.impl.element.*;
import oxy.base.event.impl.color.FunctionColorOverlay;
import oxy.base.event.impl.element.focus.FunctionFocusElement;
import oxy.base.event.impl.element.focus.FunctionUnfocusElement;
import oxy.base.event.impl.element.values.FunctionPositionElement;
import oxy.base.event.impl.element.values.FunctionRotateElement;
import oxy.base.event.impl.log.FunctionAddLog;
import oxy.base.event.impl.log.FunctionClearLog;
import oxy.base.event.impl.sound.FunctionPlaySoundEvent;
import oxy.base.event.impl.sound.FunctionSoundEvent;
import oxy.base.event.impl.sound.FunctionSoundVolumeEvent;
import oxy.base.event.impl.sound.FunctionStopSoundEvent;
import oxy.base.event.impl.transition.FunctionScreenTransition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventRegistries {
    public static Map<Class<? extends Event>, Class<? extends FunctionEvent<?>>> EVENT_TO_FUNCTION = new HashMap<>();
    static {
        EVENT_TO_FUNCTION.put(SetBackgroundEvent.class, FunctionSetBackground.class);
        EVENT_TO_FUNCTION.put(ClearBackgroundEvent.class, FunctionClearBackground.class);

        EVENT_TO_FUNCTION.put(ColorOverlayEvent.class, FunctionColorOverlay.class);
        EVENT_TO_FUNCTION.put(SetColorEvent.class, FunctionSetColor.class);

        EVENT_TO_FUNCTION.put(SoundEvent.class, FunctionSoundEvent.class);
        EVENT_TO_FUNCTION.put(PlaySoundEvent.class, FunctionPlaySoundEvent.class);
        EVENT_TO_FUNCTION.put(StopSoundEvent.class, FunctionStopSoundEvent.class);
        EVENT_TO_FUNCTION.put(SoundVolumeEvent.class, FunctionSoundVolumeEvent.class);

        EVENT_TO_FUNCTION.put(AddElementEvent.class, FunctionAddElement.class);
        EVENT_TO_FUNCTION.put(AttachElementEvent.class, FunctionAttachElement.class);
        EVENT_TO_FUNCTION.put(RemoveElementEvent.class, FunctionRemoveElement.class);
        EVENT_TO_FUNCTION.put(ElementIndexEvent.class, FunctionElementIndex.class);

        EVENT_TO_FUNCTION.put(PositionElementEvent.class, FunctionPositionElement.class);
        EVENT_TO_FUNCTION.put(RotateElementEvent.class, FunctionRotateElement.class);

        EVENT_TO_FUNCTION.put(ElementEffectEvent.class, FunctionElementEffect.class);

        EVENT_TO_FUNCTION.put(FocusElementEvent.class, FunctionFocusElement.class);
        EVENT_TO_FUNCTION.put(UnfocusElementEvent.class, FunctionUnfocusElement.class);

        EVENT_TO_FUNCTION.put(PlayAnimationEvent.class, FunctionPlayAnimation.class);
        EVENT_TO_FUNCTION.put(StopAnimationEvent.class, FunctionStopAnimation.class);

        EVENT_TO_FUNCTION.put(StartDialogueEvent.class, FunctionStartDialogue.class);
        EVENT_TO_FUNCTION.put(AddDialogueEvent.class, FunctionAddDialogue.class);
        EVENT_TO_FUNCTION.put(CloseDialogueEvent.class, FunctionCloseDialogue.class);
        EVENT_TO_FUNCTION.put(RedirectDialogueEvent.class, FunctionRedirectDialogue.class);

        EVENT_TO_FUNCTION.put(ShowOptionsEvent.class, FunctionShowOptions.class);
        EVENT_TO_FUNCTION.put(CloseOptionsEvent.class, FunctionCloseOptions.class);

        EVENT_TO_FUNCTION.put(ShowQuestionSelectionEvent.class, FunctionShowQuestionSelection.class);

        EVENT_TO_FUNCTION.put(AddLogEvent.class, FunctionAddLog.class);
        EVENT_TO_FUNCTION.put(ClearLogEvent.class, FunctionClearLog.class);

        EVENT_TO_FUNCTION.put(SpriteAnimationEvent.class, FunctionSpriteAnimation.class);

        EVENT_TO_FUNCTION.put(LockClickEvent.class, FunctionLockClick.class);
        EVENT_TO_FUNCTION.put(ShowButtonsEvent.class, FunctionShowButtons.class);

        EVENT_TO_FUNCTION.put(SetWeatherEvent.class, FunctionSetWeather.class);
        EVENT_TO_FUNCTION.put(ScreenEffectEvent.class, FunctionScreenEffect.class);

        EVENT_TO_FUNCTION.put(ScreenTransitionEvent.class, FunctionScreenTransition.class);

        EVENT_TO_FUNCTION.put(PopupEvent.class, FunctionPopup.class);

        EVENT_TO_FUNCTION = Collections.unmodifiableMap(EVENT_TO_FUNCTION);
    }
}
