package oxy.bascenario.editor.inspector.elements.events;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.utils.ImGuiUtils;

public class SoundInspector {
    public static StopSoundEvent render(StopSoundEvent event) {
        StopSoundEvent.StopSoundEventBuilder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Sound ID", 0)));
        builder.duration(ImGuiUtils.sliderInt("Fade Duration (ms)", event.getDuration(), 0, 10000));
        return builder.build();
    }

    public static SoundVolumeEvent render(SoundVolumeEvent event) {
        SoundVolumeEvent.SoundVolumeEventBuilder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Sound ID", 0)));
        builder.duration(ImGuiUtils.sliderInt("Fade Duration (ms)", (int) event.getDuration(), 0, 10000));
        builder.volume(Math.abs(ImGuiUtils.sliderFloat("Volume", event.getVolume(), 0, 1)));
        builder.easing(Easing.values()[ImGuiUtils.combo("Easing Mode", event.getEasing().ordinal(), Easing.getAlls())]);
        return builder.build();
    }

    public static PlaySoundEvent render(Scenario.Builder scenario, PlaySoundEvent event) {
        PlaySoundEvent.PlaySoundEventBuilder builder = event.toBuilder();
        builder.duration(ImGuiUtils.sliderInt("Fade Duration (ms)", (int) event.getDuration(), 0, 10000));
        builder.start(ImGuiUtils.inputFloat("Start Position (seconds)", event.getStart()));
        ImGui.separatorText("");
        builder.sound(render(scenario, event.getSound()));
        return builder.build();
    }

    public static Sound render(Scenario.Builder scenario, Sound sound) {
        final Sound.SoundBuilder builder = sound.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Sound ID", 0)));
        ImGuiUtils.pick(builder::file, scenario, "File", false, "mp3, wav, ogg");
        builder.maxVolume(Math.abs(ImGuiUtils.sliderFloat("Max Volume", sound.maxVolume(), 0, 1)));
        builder.loop(ImGuiUtils.checkbox("Loop", sound.loop()));
        return builder.build();
    }
}
