package com.bascenario.engine.scenario.event.impl;

import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.EmoticonRender;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PlayEmoticonEvent extends Event<PlayEmoticonEvent> {
    private final int spriteId;
    private final Sprite.Emoticon emoticon;
    public PlayEmoticonEvent(int spriteId, Sprite.Emoticon emoticon) {
        super(0);
        this.spriteId = spriteId;
        this.emoticon = emoticon;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        final String location = switch (emoticon.type()) {
            case SWEAT -> "assets/base/sounds/SFX_Emoticon_Motion_Sweat.wav";
            case EXCLAMATION_MARK -> "assets/base/sounds/SFX_Emoticon_Motion_Exclaim.wav";
            case ANXIETY -> "assets/base/sounds/SFX_Emoticon_Motion_Upset.wav";
            case THINKING, HESITATED -> "assets/base/sounds/SFX_Emoticon_Motion_Dot.wav";
            case SHY -> "assets/base/sounds/SFX_Emoticon_Motion_Shy.wav";
            case CHAT -> "assets/base/sounds/SFX_Emoticon_Motion_Chat.wav";
            case QUESTION_MARK -> "assets/base/sounds/SFX_Emoticon_Motion_Question.wav";
            case TWINKLE, NOTE -> "assets/base/sounds/SFX_Emoticon_Motion_Twinkle.wav";
            case SURPRISED -> "assets/base/sounds/SFX_Emoticon_Motion_Surprise.wav";
            case HEART -> "assets/base/sounds/SFX_Emoticon_Motion_Heart.wav";
            case RESPOND -> "assets/base/sounds/SFX_Emoticon_Motion_Respond.wav";
            case ANGRY -> "assets/base/sounds/SFX_Emoticon_Motion_Angry.wav";
        };

        if (this.emoticon.playSound()) {
            AudioManager.getInstance().play(location, false, 1, true);
        }

        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSpriteId() == this.spriteId) {
                render = spriteRender;
            }
        }

        if (render != null) {
            render.getEmoticons().add(new EmoticonRender(this.emoticon));
        }
    }

    @Override
    public PlayEmoticonEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();

        final Sprite.Emoticon emoticon = GsonUtil.getGson().fromJson(serialized.get("emoticon").getAsString().trim(), Sprite.Emoticon.class);
        return new PlayEmoticonEvent(serialized.get("attached-sprite").getAsInt(), emoticon);
    }

    @Override
    public JsonElement serialize(PlayEmoticonEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("attached-sprite", src.spriteId);
        serialized.add("emoticon", GsonUtil.toJson(src.emoticon));
        return serialized;
    }
}
