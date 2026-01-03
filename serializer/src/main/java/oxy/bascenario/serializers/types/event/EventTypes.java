package oxy.bascenario.serializers.types.event;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.LockClickEvent;
import oxy.bascenario.api.event.ShowButtonsEvent;
import oxy.bascenario.api.event.background.ClearBackgroundEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.api.event.dialogue.*;
import oxy.bascenario.api.event.element.*;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.*;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.event.impl.LockClickType;
import oxy.bascenario.serializers.types.event.impl.ShowButtonsType;
import oxy.bascenario.serializers.types.event.impl.animation.PlayAnimationType;
import oxy.bascenario.serializers.types.event.impl.animation.SpriteAnimationType;
import oxy.bascenario.serializers.types.event.impl.animation.StopAnimationType;
import oxy.bascenario.serializers.types.event.impl.background.ClearBackgroundType;
import oxy.bascenario.serializers.types.event.impl.background.SetBackgroundType;
import oxy.bascenario.serializers.types.event.impl.color.ColorOverlayType;
import oxy.bascenario.serializers.types.event.impl.color.SetColorType;
import oxy.bascenario.serializers.types.event.impl.dialogue.*;
import oxy.bascenario.serializers.types.event.impl.element.*;
import oxy.bascenario.serializers.types.event.impl.element.values.PositionElementType;
import oxy.bascenario.serializers.types.event.impl.element.values.RotateElementType;
import oxy.bascenario.serializers.types.event.impl.sound.*;

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

        put(ColorOverlayEvent.class, new ColorOverlayType());
        put(SetColorEvent.class, new SetColorType());

        put(StopAnimationEvent.class, new StopAnimationType());
        put(SpriteAnimationEvent.class, new SpriteAnimationType());
        put(PlayAnimationEvent.class, new PlayAnimationType());

        put(SetBackgroundEvent.class, new SetBackgroundType());
        put(ClearBackgroundEvent.class, new ClearBackgroundType());

        put(LockClickEvent.class, new LockClickType());
        put(ShowButtonsEvent.class, new ShowButtonsType());
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

    @Override
    public void write(Event o, ByteBuf buf) {
        final TypeWithName<?> type = CLASS_TO_TYPE.get(o.getClass());
        if (type == null) {
            throw new RuntimeException("Invalid event class type: " + o.getClass() + "!");
        }

        buf.writeInt(type.type().hashCode());
        type.writeElement(o, buf);
    }

    @Override
    public Event read(ByteBuf buf) {
        final int id = buf.readInt();
        final TypeWithName<?> type = ID_TO_TYPE.get(id);
        if (type == null) {
            throw new RuntimeException("Invalid event with id: " + id + "!");
        }

        return (Event) type.read(buf);
    }
}
