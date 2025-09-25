package com.bascenario.engine.scenario.event;

import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.event.impl.LocationInfoEvent;
import com.bascenario.engine.scenario.event.impl.PlayEmoticonEvent;
import com.bascenario.engine.scenario.event.impl.QueueEventEvent;
import com.bascenario.engine.scenario.event.impl.SetPopupEvent;
import com.bascenario.engine.scenario.event.impl.background.SetBackgroundEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.CloseDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.PlayDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.RedirectDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.ShowDialogueOptionEvent;
import com.bascenario.engine.scenario.event.impl.lock.LockClickEvent;
import com.bascenario.engine.scenario.event.impl.sound.PlaySoundEvent;
import com.bascenario.engine.scenario.event.impl.sound.StopSoundEvent;
import com.bascenario.engine.scenario.event.impl.sprite.*;
import com.bascenario.engine.scenario.event.impl.sprite.mini.SpriteShakeEvent;
import com.google.gson.*;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventSerializer implements JsonSerializer<Event<?>>, JsonDeserializer<Event<?>> {
    private static final Sound DUMMY_SOUND = new Sound("dummy", 0, 0, Integer.MIN_VALUE);

    private static final Map<Class<?>, Event<?>> CLASS_TO_DUMMY = new HashMap<>();
    public static Map<Class<?>, Event<?>> getEventClassesToDummies() {
        return Collections.unmodifiableMap(CLASS_TO_DUMMY);
    }
    private static final Map<String, Event<?>> TYPE_TO_DUMMY = new HashMap<>();

    static {
        // Background.
        register(SetBackgroundEvent.class, new SetBackgroundEvent(null));
        // Dialogue (options)
        register(CloseDialogueEvent.class, new CloseDialogueEvent());
        register(PlayDialogueEvent.class, new PlayDialogueEvent(null));
        register(RedirectDialogueEvent.class, new PlayDialogueEvent(null));
        register(ShowDialogueOptionEvent.class, new ShowDialogueOptionEvent(null));
        // Lock
        register(LockClickEvent.class, new LockClickEvent(false));
        // Sound
        register(PlaySoundEvent.class, new PlaySoundEvent(DUMMY_SOUND, false));
        register(StopSoundEvent.class, new StopSoundEvent(Integer.MIN_VALUE));
        // Sprite
        register(SpriteShakeEvent.class, new SpriteShakeEvent(-1, 0, 0)); // (mini event)
        register(AddSpriteEvent.class, new AddSpriteEvent(null, -1));
        register(RemoveSpriteEvent.class, new RemoveSpriteEvent(-1));
        register(SpriteAnimationEvent.class, new SpriteAnimationEvent(-1, null, 0, false));
        register(SpriteFadeEvent.class, new SpriteFadeEvent(-1, 0, 0));
        register(SpriteLocationEvent.class, new SpriteLocationEvent(-1, 0, 0, 0));
        register(SpriteScaleEvent.class, new SpriteScaleEvent(-1, 0, 0));
        // Popups
        register(LocationInfoEvent.class, new LocationInfoEvent(null));
        register(SetPopupEvent.class, new SetPopupEvent(null));
        // Emoticon
        register(PlayEmoticonEvent.class, new PlayEmoticonEvent(-1, null));
        // Queue
        register(QueueEventEvent.class, new QueueEventEvent(0, null));
    }
    
    private static void register(Class<?> klass, Event<?> event) {
        CLASS_TO_DUMMY.put(klass, event);
        TYPE_TO_DUMMY.put(event.type(), event);
    }

    @Override
    public JsonElement serialize(Event<?> src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("type", src.type());
        src.serialize(serialized);
        return serialized;
    }

    @Override
    public Event<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return (Event<?>) TYPE_TO_DUMMY.get(serialized.get("type").getAsString()).deserialize(serialized);
    }
}
