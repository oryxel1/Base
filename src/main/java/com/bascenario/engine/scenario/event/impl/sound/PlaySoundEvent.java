package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.FileUtil;
import com.bascenario.util.GsonUtil;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;
import imgui.ImGui;

public class PlaySoundEvent extends Event<PlaySoundEvent> {
    private final Sound sound;
    private boolean loop;

    public PlaySoundEvent(Sound sound, boolean loop) {
        super(sound.fadeIn() > 0 ? sound.fadeIn() + 100 : 0);
        this.sound = sound;
        this.loop = loop;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (sound.fadeIn() <= 0) {
            AudioManager.getInstance().play(this.sound.soundId(), this.sound.path(), this.loop, this.sound.maxVolume(), false);
        } else {
            AudioManager.getInstance().playFadeIn(this.sound.soundId(), this.sound.path(), this.sound.fadeIn(), this.loop, this.sound.maxVolume(), false);
        }
    }

    @Override
    public void renderImGui() {
        ImGui.text("Sound Path: " + sound.path());
        ImGui.sameLine();
        if (ImGui.button("Browse##" + ImGuiUtil.COUNTER++)) {
            final String path = FileUtil.pickFile("mp3", "wav", "ogg");
            if (!path.isBlank()) {
                sound.path(path);
            }
        }

        sound.maxVolume(ImGuiUtil.sliderFloat("Max Volume", sound.maxVolume(), 0, 1));

        if (ImGui.checkbox("Fade In", sound.fadeIn() > 0)) {
            sound.fadeIn(sound.fadeIn() <= 0 ? 1 : 0);
        }

        if (sound.fadeIn() > 0) {
            sound.fadeIn(ImGuiUtil.sliderInt("Fade In Duration", (int) sound.fadeIn(), 0, 10000));

        }
        int soundId = ImGuiUtil.inputInt("Sound ID", sound.soundId());
        if (soundId >= 0 && soundId != sound.soundId()) {
            sound.soundId(soundId);
        }

        this.loop = ImGuiUtil.checkbox("Loop", false);
    }

    @Override
    public PlaySoundEvent defaultEvent() {
        return new PlaySoundEvent(new Sound("", 0.5F, 0, 1), false);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("sound", GsonUtil.toJson(this.sound));
        serialized.addProperty("loop", this.loop);
    }

    @Override
    public PlaySoundEvent deserialize(JsonObject serialized) {
        Sound sound = GsonUtil.getGson().fromJson(serialized.get("sound"), Sound.class);
        sound = new Sound(sound.path(), sound.maxVolume(), sound.fadeIn(), Math.abs(sound.soundId()));

        return new PlaySoundEvent(sound, serialized.get("loop").getAsBoolean());
    }

    @Override
    public String type() {
        return "play-sound";
    }
}
