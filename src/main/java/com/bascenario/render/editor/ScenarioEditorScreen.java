package com.bascenario.render.editor;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.Timestamp;
import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.event.impl.QueueEventEvent;
import com.bascenario.managers.AudioManager;
import com.bascenario.render.api.Screen;
import com.bascenario.render.scenario.ScenarioPreviewScreen;
import com.bascenario.util.FileUtil;
import com.bascenario.util.render.ImGuiUtil;
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

import java.util.ArrayList;
import java.util.List;
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
        }
    }

    private Timestamp timestamp;
    private void renderTimestampEditor() {
        ImGui.begin("Timestamp Editor");
        if (this.timestamp == null) {
//            timestamp = scenario.timestamps().get(0);
            ImGui.end();
            return;
        }

        ImGuiUtil.COUNTER = 0;
        if (ImGui.checkbox("Wait For Dialogue##" + ImGuiUtil.COUNTER++, timestamp.waitForDialogue())) {
            timestamp.waitForDialogue(!timestamp.waitForDialogue());
        }

        timestamp.time(ImGuiUtil.inputInt("Time Till Next", (int) timestamp.time()));

        ImGui.separatorText("Events");

        for (int i = 0; i < timestamp.events().size(); i++) {
            Event<?> event = timestamp.events().get(i);
            final Event<?> renderEvent;

            long queueTime = 0;
            if (event instanceof QueueEventEvent queueEventEvent) {
                renderEvent = queueEventEvent.getQueuedEvent();
                queueTime = queueEventEvent.getDuration();
            } else {
                renderEvent = event;
            }

            if (ImGui.collapsingHeader("Event (" + renderEvent.type() + ")##" + ImGuiUtil.COUNTER++)) {
                queueTime = ImGuiUtil.sliderInt("Queue Time", (int) queueTime, 0, 10000);
                ImGui.separator();
                renderEvent.renderImGui();
            }

            boolean changed = false;
            if (queueTime > 0) {
                if (event instanceof QueueEventEvent queue) {
                    queue.setDuration(queueTime);
                } else {
                    event = new QueueEventEvent(queueTime, event);
                    changed = true;
                }
            } else {
                if (event instanceof QueueEventEvent queue) {
                    event = queue.getQueuedEvent();
                    changed = true;
                }
            }

            if (changed) {
                timestamp.events().set(i, event);
            }
        }

        ImGui.end();
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
            this.scenario.name(ImGuiUtil.inputText("Name", this.scenario.name()));
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
            if (ImGui.button("Browse##" + ImGuiUtil.COUNTER++)) {
                final String path = FileUtil.pickFile("jpg", "png");
                if (!path.isBlank()) {
                    background.path(path);
                }
            }
            background.fadeIn(ImGui.checkbox("Fade in", background.fadeIn()));
            background.fadeOut(ImGui.checkbox("Fade out", background.fadeOut()));

            if (!background.equals(NULL_BACKGROUND) && this.scenario.previewBackground() == null) {
                this.scenario.previewBackground(background);
            }

            if (ImGui.button("Done!")) {
                ImGui.closeCurrentPopup();
            }
            ImGui.endPopup();
        }

        ImGui.end();
    }
}
