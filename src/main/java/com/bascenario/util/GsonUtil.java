package com.bascenario.util;

import com.bascenario.engine.scenario.elements.Sound;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.Getter;

public final class GsonUtil {
    private static final Sound DUMMY_SOUND = new Sound("dummy", 0, 0);

    @Getter
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
            // Background.
            .registerTypeAdapter(SetBackgroundEvent.class, new SetBackgroundEvent(null))
            // Dialogue (options)
            .registerTypeAdapter(CloseDialogueEvent.class, new CloseDialogueEvent())
            .registerTypeAdapter(PlayDialogueEvent.class, new PlayDialogueEvent(null))
            .registerTypeAdapter(RedirectDialogueEvent.class, new PlayDialogueEvent(null))
            .registerTypeAdapter(ShowDialogueOptionEvent.class, new ShowDialogueOptionEvent(null))
            // Lock
            .registerTypeAdapter(LockClickEvent.class, new LockClickEvent(false))
            // Sound
            .registerTypeAdapter(PlaySoundEvent.class, new PlaySoundEvent(DUMMY_SOUND, false))
            .registerTypeAdapter(StopSoundEvent.class, new StopSoundEvent(DUMMY_SOUND))
            // Sprite
            .registerTypeAdapter(SpriteShakeEvent.class, new SpriteShakeEvent(-1, 0, 0)) // (mini event)
            .registerTypeAdapter(AddSpriteEvent.class, new AddSpriteEvent(null, -1))
            .registerTypeAdapter(RemoveSpriteEvent.class, new RemoveSpriteEvent(-1))
            .registerTypeAdapter(SpriteAnimationEvent.class, new SpriteAnimationEvent(-1, null, 0, false))
            .registerTypeAdapter(SpriteFadeEvent.class, new SpriteFadeEvent(-1, 0, 0))
            .registerTypeAdapter(SpriteLocationEvent.class, new SpriteLocationEvent(-1, 0, 0, 0))
            .registerTypeAdapter(SpriteScaleEvent.class, new SpriteScaleEvent(-1, 0, 0))
            // Popups
            .registerTypeAdapter(LocationInfoEvent.class, new LocationInfoEvent(null))
            .registerTypeAdapter(SetPopupEvent.class, new SetPopupEvent(null))
            // Emoticon
            .registerTypeAdapter(PlayEmoticonEvent.class, new PlayEmoticonEvent(-1, null))
            // Queue
            .registerTypeAdapter(QueueEventEvent.class, new QueueEventEvent(0, null))
            .create();

    public static JsonObject toJson(final Object object) {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(object).trim(), JsonObject.class);
    }
}
