package oxy.bascenario.editor.inspector.impl.events;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.miniuis.AssetsUI;
import oxy.bascenario.editor.utils.AudioUtils;
import oxy.bascenario.editor.utils.SoundAsElement;
import oxy.bascenario.utils.ImGuiUtils;

public class SoundInspector {
    public static SoundAsElement render(Scenario.Builder scenario, SoundAsElement sound) {
        SoundAsElement.Builder builder = sound.toBuilder();
        final FileInfo old =  sound.sound().file();
        Sound newSound = render(sound.sound());
        builder.sound(newSound);
        ImGui.separatorText("");
        builder.in(ImGuiUtils.sliderInt("Fade In (ms)", sound.in(), 0, (int) Math.min(10000, sound.max())));
        builder.out(ImGuiUtils.sliderInt("Fade Out (ms)", sound.out(), 0, (int) Math.min(10000, sound.max())));
        builder.start(ImGuiUtils.sliderFloat("Start Position (seconds)", sound.start(), 0, (float) sound.max() / 1000L));
        if ((old == null || !old.equals(newSound.file())) && newSound.file() != null) {
            builder.max(AudioUtils.toDuration(scenario.name(), newSound.file()));
        }
        return builder.build();
    }
//
//    public static StopSoundEvent render(StopSoundEvent event) {
//        StopSoundEvent.Builder builder = event.toBuilder();
//        builder.id(Math.abs(ImGuiUtils.inputInt("Sound ID", 0)));
//        builder.duration(ImGuiUtils.sliderInt("Fade Duration (ms)", event.getDuration(), 0, 10000));
//        return builder.build();
//    }
//
    public static SoundVolumeEvent render(SoundVolumeEvent event) {
        SoundVolumeEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Sound ID", 0)));
        builder.duration(ImGuiUtils.sliderInt("Fade Duration (ms)", (int) event.duration(), 0, 10000));
        builder.volume(Math.abs(ImGuiUtils.sliderFloat("Volume", event.volume(), 0, 1)));
        builder.easing(Easing.values()[ImGuiUtils.combo("Easing Mode", event.easing().ordinal(), Easing.getAlls())]);
        return builder.build();
    }
//
//    public static PlaySoundEvent render(Scenario.Builder scenario, PlaySoundEvent event) {
//        PlaySoundEvent.Builder builder = event.toBuilder();
//        builder.duration(ImGuiUtils.sliderInt("Fade Duration (ms)", (int) event.getDuration(), 0, 10000));
//        builder.start(ImGuiUtils.inputFloat("Start Position (seconds)", event.getStart()));
//        ImGui.separatorText("");
//        builder.sound(render(scenario, event.getSound()));
//        return builder.build();
//    }

    private static FileInfo last;

    public static Sound render(Sound sound) {
        final Sound.Builder builder = sound.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Sound ID", 0)));
        AssetsUI.pick("Pick Sound!", file -> last = file,  "mp3,wav,ogg");
        builder.maxVolume(Math.abs(ImGuiUtils.sliderFloat("Max Volume", sound.maxVolume(), 0, 1)));
//        builder.loop(ImGuiUtils.checkbox("Loop", sound.loop()));

        if (last != null) {
            builder.file(last);
            last = null;
        }
        return builder.build();
    }
}
