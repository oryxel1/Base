package oxy.bascenario.editor.utils;

import oxy.bascenario.api.event.LockClickEvent;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.background.ClearBackgroundEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.api.event.dialogue.*;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.render.elements.LocationInfo;

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

            default -> object.getClass().getSimpleName();
        };
    }
}
