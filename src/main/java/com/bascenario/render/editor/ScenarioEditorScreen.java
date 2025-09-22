package com.bascenario.render.editor;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.managers.AudioManager;
import com.bascenario.render.api.Screen;
import com.bascenario.render.scenario.ScenarioPreviewScreen;
import com.bascenario.util.FileUtil;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

// Worse code I'm ever going to write hooo rayyyy!
@RequiredArgsConstructor
public class ScenarioEditorScreen extends Screen {
    private static final Background NULL_BACKGROUND = new Background("", false, false);
    private static final Sound NULL_SOUND = new Sound("", 1, 0, 0);

    private final Scenario.Builder scenario;
    private ScenarioPreviewScreen preview;

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        this.renderScenarioInfo();
        this.renderTimestampEditor();

        if (this.preview != null) {
            this.preview.render(positionMatrix, window, mouseX, mouseY);
            if (this.preview.isDone()) {
                this.stopPreviewingPreview();
            }
        }
    }

    private void renderTimestampEditor() {
    }

    private boolean editPreviewBackground, editPreviewSound, editPreviewName;
    private void renderScenarioInfo() {
        // Scenario details.
        ImGui.setNextWindowPos(new ImVec2(10, 10), ImGuiCond.Always, new ImVec2(0, 0));
        ImGui.setNextWindowBgAlpha(0.35F);

        final AtomicInteger totalEventSize = new AtomicInteger();
        this.scenario.timestamps().forEach(t -> totalEventSize.addAndGet(t.events().size()));

        ImGui.begin("Scenario Name", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoSavedSettings | ImGuiWindowFlags.NoFocusOnAppearing | ImGuiWindowFlags.NoNav);
        ImGui.text(this.scenario.name());
        ImGui.text("Preview background: " + this.scenario.previewBackground());
        ImGui.text("Preview sound: " + this.scenario.previewSound());
        ImGui.text("Total timestamps: " + this.scenario.timestamps().size() + " (total events: " + totalEventSize.get() + ")");

        // Oof, ImGui doesn't like calling popup inside menuItem, so hack around that.
        if (this.editPreviewBackground) {
            ImGui.openPopup("Preview Background");
            this.editPreviewBackground = false;
        }

        if (this.editPreviewSound) {
            ImGui.openPopup("Preview Sound");
            this.editPreviewSound = false;
        }

        if (this.editPreviewName) {
            ImGui.openPopup("Preview Name");
            this.editPreviewName = false;
        }

        if (ImGui.beginPopupContextWindow()){
            if (ImGui.menuItem("Edit preview name")) {
                this.editPreviewName = true;
            }
            if (ImGui.menuItem("Edit preview background")) {
                this.editPreviewBackground = true;
            }
            if (ImGui.menuItem("Edit preview sound")) {
                this.editPreviewSound = true;
            }

            ImGui.endPopup();
        }

        if (ImGui.beginPopupModal("Preview Name", ImGuiWindowFlags.AlwaysAutoResize)) {
            final ImString string = new ImString(this.scenario.name());
            ImGui.inputText("Text", string, ImGuiInputTextFlags.NoHorizontalScroll | ImGuiInputTextFlags.CallbackResize);
            if (!Objects.equals(string.get(), this.scenario.name())) {
                this.scenario.name(string.get());
            }
            if (ImGui.button("Done!")) {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }

        if (ImGui.beginPopupModal("Preview Sound", ImGuiWindowFlags.AlwaysAutoResize)) {
            Sound sound = this.scenario.previewSound();
            if (sound == null) {
                sound = new Sound("", 1, 0, 0);
            }

            ImGui.text("Sound path: " + sound.path());
            ImGui.sameLine();
            if (ImGui.button("Browse")) {
                final String path = FileUtil.pickFile("mp3", "wav", "ogg");
                if (!path.isBlank()) {
                    sound.path(path);
                }
            }

            float[] maxVolume = new float[] {sound.maxVolume()};
            ImGui.sliderFloat("Max volume", maxVolume, 0, 1);
            if (maxVolume[0] != sound.maxVolume()) {
                sound.maxVolume(maxVolume[0]);
            }

            if (ImGui.checkbox("Fade in", sound.fadeIn() > 0)) {
                sound.fadeIn(sound.fadeIn() <= 0 ? 1 : 0);
            }
            if (sound.fadeIn() > 0) {
                int[] fadeIn = new int[] {(int) sound.fadeIn()};
                ImGui.sliderInt("Fade in duration", fadeIn, 0, 10000);
                if (fadeIn[0] != sound.fadeIn()) {
                    sound.fadeIn(fadeIn[0]);
                }
            }
            final ImInt soundIdIm = new ImInt(sound.soundId());
            ImGui.inputInt("Sound id", soundIdIm);
            int soundId = soundIdIm.get();
            if (soundId >= 0 && soundId != sound.soundId()) {
                sound.soundId(soundId);
            }

            if (!sound.equals(NULL_SOUND) && this.scenario.previewSound() == null) {
                this.scenario.previewSound(sound);
            }

            if (ImGui.button("Done!")) {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }

        if (ImGui.beginPopupModal("Preview Background", ImGuiWindowFlags.AlwaysAutoResize)) {
            Background background = this.scenario.previewBackground();
            if (background == null) {
                background = new Background("", false, false);
            }

            ImGui.text("Background path: " + background.path());
            ImGui.sameLine();
            if (ImGui.button("Browse")) {
                final String path = FileUtil.pickFile("jpg", "png");
                if (!path.isBlank()) {
                    background.path(path);
                }
            }
            if (ImGui.checkbox("Fade in", background.fadeIn())) {
                background.fadeIn(!background.fadeIn());
                background = new Background(background.path(), !background.fadeIn(), background.fadeOut());
            }
            if (ImGui.checkbox("Fade out", background.fadeOut())) {
                background.fadeOut(!background.fadeOut());
            }

            if (!background.equals(NULL_BACKGROUND) && this.scenario.previewBackground() == null) {
                this.scenario.previewBackground(background);
            }

            if (ImGui.button("Done!")) {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }

        ImGui.end();

        // Timestamps and event.
        // Still unsure how I should do this.
//        ImGui.begin("Timestamps and events.");
//        if (ImGui.collapsingHeader("Preview")) {
//            if (ImGui.button("Play")) {
//                if (this.preview != null) {
//                    this.stopPreviewingPreview();
//                }
//
//                this.preview = new ScenarioPreviewScreen(scenario.build());
//                this.preview.setEditing(true);
//            }
//
//        }
//
//        ImGui.separatorText("Main Scenario");
//
//        int index = -1;
//        List<Timestamp> timestamps = new ArrayList<>();
//        boolean changed = false;
//        for (Timestamp timestamp : this.scenario.timestamps()) {
//            index++

//            if (ImGui.collapsingHeader("Timestamp##" + index)) {
//                if (ImGui.checkbox("Wait For Dialogue##" + index, timestamp.waitForDialogue())) {
//                    timestamp = new Timestamp(!timestamp.waitForDialogue(), timestamp.time(), timestamp.events());
//                    changed = true;
//                }
//
//                final ImInt time = new ImInt((int) timestamp.time());
//                ImGui.inputInt("Time Till Next", time);
//                if (time.get() != timestamp.time()) {
//                    timestamp = new Timestamp(timestamp.waitForDialogue(), time.get(), timestamp.events());
//                    changed = true;
//                }
//            }
//
//            timestamps.add(timestamp);
//        }
//
//        if (changed) {
//            this.scenario.timestamps().clear();
//            this.scenario.timestamps().addAll(timestamps);
//        }
//
//        ImGui.end();
    }

    private void stopPreviewingPreview() {
        if (this.preview.getScenario().getPreviewSound() != null) {
            AudioManager.getInstance().stop(this.preview.getScenario().getPreviewSound().soundId());
        }
        this.preview = null;
    }
}
