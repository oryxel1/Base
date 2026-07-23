package oxy.base.serializers.types.event;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.*;
import oxy.base.api.event.background.ClearBackgroundEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.event.animation.PlayAnimationEvent;
import oxy.base.api.event.animation.SpriteAnimationEvent;
import oxy.base.api.event.animation.StopAnimationEvent;
import oxy.base.api.event.api.Event;
import oxy.base.api.event.color.ColorOverlayEvent;
import oxy.base.api.event.color.SetColorEvent;
import oxy.base.api.event.dialogue.*;
import oxy.base.api.event.element.*;
import oxy.base.api.event.element.focus.FocusElementEvent;
import oxy.base.api.event.element.focus.UnfocusElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.event.element.values.RotateElementEvent;
import oxy.base.api.event.log.AddLogEvent;
import oxy.base.serializers.base.Type;
import oxy.base.serializers.base.TypeWithName;
import oxy.base.serializers.types.event.impl.*;
import oxy.base.serializers.types.event.impl.animation.PlayAnimationType;
import oxy.base.serializers.types.event.impl.animation.SpriteAnimationType;
import oxy.base.serializers.types.event.impl.animation.StopAnimationType;
import oxy.base.serializers.types.event.impl.background.ClearBackgroundType;
import oxy.base.serializers.types.event.impl.background.SetBackgroundType;
import oxy.base.serializers.types.event.impl.color.ColorOverlayType;
import oxy.base.serializers.types.event.impl.color.SetColorType;
import oxy.base.serializers.types.event.impl.dialogue.*;
import oxy.base.serializers.types.event.impl.element.*;
import oxy.base.serializers.types.event.impl.element.focus.FocusElementType;
import oxy.base.serializers.types.event.impl.element.focus.UnfocusElementType;
import oxy.base.serializers.types.event.impl.element.values.PositionElementType;
import oxy.base.serializers.types.event.impl.element.values.RotateElementType;
import oxy.base.serializers.types.event.impl.log.AddLogType;
import oxy.base.serializers.types.event.impl.log.ClearLogType;
import oxy.base.serializers.types.event.impl.sound.*;
import oxy.base.serializers.types.event.impl.transition.ScreenTransitionType;
import oxy.base.api.event.sound.PlaySoundEvent;
import oxy.base.api.event.sound.SoundEvent;
import oxy.base.api.event.sound.SoundVolumeEvent;
import oxy.base.api.event.sound.StopSoundEvent;

import java.util.HashMap;
import java.util.Map;

public class EventTypes implements Type<Event> {
    private static final Map<Class<?>, TypeWithName<?>> CLASS_TO_TYPE = new HashMap<>();
    private static final Map<Integer, TypeWithName<?>> ID_TO_TYPE = new HashMap<>();
    private static void put(Class<? extends Event> klass, TypeWithName<?> type) {
        CLASS_TO_TYPE.put(klass, type);
        ID_TO_TYPE.put(type.type().hashCode(), type);
    }

    static {
        put(StopSoundEvent.class, new StopSoundType());
        put(SoundVolumeEvent.class, new SoundVolumeType());
        put(SoundEvent.class, new SoundEventType());
        put(PlaySoundEvent.class, new PlaySoundType());

        put(RemoveElementEvent.class, new RemoveElementType());
        put(ElementIndexEvent.class, new ElementIndexType());
        put(ElementEffectEvent.class, new ElementEffectType());
        put(AttachElementEvent.class, new AttachElementType());
        put(AddElementEvent.class, new AddElementType());

        put(PositionElementEvent.class, new PositionElementType());
        put(RotateElementEvent.class, new RotateElementType());

        put(StartDialogueEvent.class, new StartDialogueType());
        put(ShowOptionsEvent.class, new ShowOptionsType());
        put(CloseOptionsEvent.class, new CloseOptionsType());
        put(CloseDialogueEvent.class, new CloseDialogueType());
        put(AddDialogueEvent.class, new AddDialogueType());
        put(RedirectDialogueEvent.class, new RedirectDialogueType());

        put(ShowQuestionSelectionEvent.class, new ShowQuestionSelectionType());

        put(AddLogEvent.class, new AddLogType());
        put(ClearLogEvent.class, new ClearLogType());

        put(ColorOverlayEvent.class, new ColorOverlayType());
        put(SetColorEvent.class, new SetColorType());

        put(FocusElementEvent.class, new FocusElementType());
        put(UnfocusElementEvent.class, new UnfocusElementType());

        put(StopAnimationEvent.class, new StopAnimationType());
        put(SpriteAnimationEvent.class, new SpriteAnimationType());
        put(PlayAnimationEvent.class, new PlayAnimationType());

        put(SetBackgroundEvent.class, new SetBackgroundType());
        put(ClearBackgroundEvent.class, new ClearBackgroundType());

        put(LockClickEvent.class, new LockClickType());
        put(ShowButtonsEvent.class, new ShowButtonsType());

        put(SetWeatherEvent.class, new SetWeatherType());
        put(ScreenEffectEvent.class, new ScreenEffectType());

        put(ScreenTransitionEvent.class, new ScreenTransitionType());

        put(PopupEvent.class, new PopupType());
    }

    @Override
    public JsonElement write(Event o) {
        final TypeWithName<?> type = CLASS_TO_TYPE.get(o.getClass());
        if (type == null) {
            throw new RuntimeException("Invalid event class type: " + o.getClass() + "!");
        }

        final JsonObject object = new JsonObject();
        object.addProperty("type", type.type());
        object.add("event", type.writeElement(o));
        return object;
    }

    @Override
    public Event read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final int id = object.get("type").getAsString().hashCode();
        final TypeWithName<?> type = ID_TO_TYPE.get(id);
        if (type == null) {
            throw new RuntimeException("Invalid event with id: " + id + "!");
        }

        return (Event) type.read(object.get("event"));
    }
}
