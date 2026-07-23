package oxy.base.editor.utils;

import oxy.base.api.event.*;
import oxy.base.api.event.animation.PlayAnimationEvent;
import oxy.base.api.event.animation.SpriteAnimationEvent;
import oxy.base.api.event.animation.StopAnimationEvent;
import oxy.base.api.event.background.ClearBackgroundEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.event.color.ColorOverlayEvent;
import oxy.base.api.event.color.SetColorEvent;
import oxy.base.api.event.dialogue.*;
import oxy.base.api.event.element.ClearLogEvent;
import oxy.base.api.event.element.ElementEffectEvent;
import oxy.base.api.event.element.focus.FocusElementEvent;
import oxy.base.api.event.element.focus.UnfocusElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.event.element.values.RotateElementEvent;
import oxy.base.api.event.log.AddLogEvent;
import oxy.base.api.event.sound.SoundVolumeEvent;
import oxy.base.api.render.elements.LocationInfo;
import oxy.base.api.render.elements.image.AnimatedImage;
import oxy.base.api.render.elements.text.AnimatedText;

public class NameUtils {
    public static String name(Object object) {
        return switch (object) {
            // Events part.
            case PlayAnimationEvent ignored -> "Play Animation";
            case SpriteAnimationEvent ignored -> "Play Sprite Animation";
            case StopAnimationEvent ignored -> "Stop Animation";

            case StartDialogueEvent ignored -> "Start Dialogue";
            case AddDialogueEvent ignored -> "Add Dialogue";
            case ShowOptionsEvent ignored -> "Show Options";
            case ShowQuestionSelectionEvent ignored -> "Show Question And Answers";

            case CloseDialogueEvent ignored -> "Close Dialogue";
            case CloseOptionsEvent ignored -> "Close Options";

            case PositionElementEvent event -> switch (event.type()) {
                case POSITION -> "Move Object";
                case OFFSET -> "Offset Object";
                case SCALE -> "Scale Object";
                case PIVOT -> "Pivot Object";
            };
            case RotateElementEvent ignored -> "Rotate Object";

            case ElementEffectEvent event -> switch (event.type()) {
                case ADD -> "Apply Effect";
                case REMOVE -> "Remove Effect";
            };

            case ColorOverlayEvent event -> {
                if (event.id().isPresent()) {
                    yield "Color Overlay";
                } else {
                    yield "Screen Color";
                }
            }
            case SetColorEvent ignored -> "Set Color";

            case SetBackgroundEvent ignored -> "Set Background";
            case ClearBackgroundEvent ignored -> "Clear Background";

            case SoundVolumeEvent ignored -> "Sound Volume";
            case LockClickEvent ignored -> "Lock Click";

            case LocationInfo ignored -> "Location Info";
            case AnimatedText ignored -> "Typing Text";

            case SoundAsElement ignored -> "Sound";
            case AnimatedImage ignored -> "Gif";

            case ShowButtonsEvent ignored -> "Show Buttons";
            case FocusElementEvent ignored -> "Focus Object";
            case UnfocusElementEvent ignored -> "Un-focus Object";

            case SetWeatherEvent ignored -> "Set Weather";
            case PopupEvent ignored -> "Image Popup";
            case ScreenTransitionEvent ignored -> "Screen Transition";
            case ScreenEffectEvent ignored -> "Screen Effect";

            case AddLogEvent ignored -> "Add Log";
            case ClearLogEvent ignored -> "Clear Log";

            default -> object.getClass().getSimpleName();
        };
    }
}
