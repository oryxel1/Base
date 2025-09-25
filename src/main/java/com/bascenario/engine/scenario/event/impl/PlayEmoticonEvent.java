package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.Emoticon;
import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.EmoticonRender;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;
import imgui.ImGui;
import imgui.type.ImInt;

public class PlayEmoticonEvent extends Event<PlayEmoticonEvent> {
    private int spriteId;
    private final Emoticon emoticon;
    public PlayEmoticonEvent(int spriteId, Emoticon emoticon) {
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
    public void renderImGui() {
        this.spriteId = ImGuiUtil.inputInt("Sprite ID", this.spriteId);
        ImGui.separatorText("Emoticon");

        ImInt emoticonTypeIndex = new ImInt(emoticon.type().ordinal());
        ImGui.combo("Emoticon Type##" + ImGuiUtil.COUNTER++, emoticonTypeIndex, Emoticon.EmoticonType.getAlls());
        if (emoticonTypeIndex.get() != emoticon.type().ordinal()) {
            emoticon.type(Emoticon.EmoticonType.values()[emoticonTypeIndex.get()]);
        }
        emoticon.duration(ImGuiUtil.sliderInt("Duration", 1, 10000, (int) emoticon.duration()));
        emoticon.offsetX(ImGuiUtil.sliderFloat("Offset X", -1000, 1000, emoticon.offsetX()));
        emoticon.offsetY(ImGuiUtil.sliderFloat("Offset Y", -1000, 1000, emoticon.offsetY()));
        emoticon.playSound(ImGuiUtil.checkbox("Play Sound", emoticon.playSound()));
    }

    @Override
    public PlayEmoticonEvent defaultEvent() {
        return new PlayEmoticonEvent(0, new Emoticon(5000, 0, 0, Emoticon.EmoticonType.HEART, true));
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("attached-sprite", this.spriteId);
        serialized.add("emoticon", GsonUtil.toJson(this.emoticon));
    }

    @Override
    public PlayEmoticonEvent deserialize(JsonObject serialized) {
        final Emoticon emoticon = GsonUtil.getGson().fromJson(serialized.get("emoticon"), Emoticon.class);
        return new PlayEmoticonEvent(serialized.get("attached-sprite").getAsInt(), emoticon);
    }

    @Override
    public String type() {
        return "play-emoticon";
    }
}
