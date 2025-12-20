package oxy.bascenario.editor;

import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import lombok.Getter;
import lombok.Setter;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.element.ElementAdder;
import oxy.bascenario.editor.element.EventAdder;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.inspector.Inspector;

import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ThinGLUtils;

public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario.Builder scenario;
    private final Timeline timeline;
    private final ElementAdder elementAdder;
    private final EventAdder eventAdder;
    private final Inspector inspector;

    @Getter @Setter
    private Object dragging;

    public ScenarioEditorScreen(Scenario.Builder scenario) {
        this.scenario = scenario;
        this.timeline = new Timeline(this, scenario);
        this.elementAdder = new ElementAdder(this, this.timeline);
        this.eventAdder = new EventAdder(this, this.timeline);
        this.inspector = new Inspector(this, this.timeline);
    }

    @Override
    public void show() {
        this.timeline.init();
    }

    // Ideally we mostly render thing on our own, but ImGui is used here since its rendering system and docking system is quite nice.
    @Override
    public void render(float delta) {
        ImGui.dockSpaceOverViewport(0, new ImGuiViewport(0), ImGuiDockNodeFlags.PassthruCentralNode);

        renderMenuBar();
        timeline.render();
        elementAdder.render();
        eventAdder.render();
        inspector.render();

//        scenario.timestamps().clear();
//        scenario.timestamps().addAll(TrackParser.parse(timeline.getTracks()));

        ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0, 0, 0, 0);
        ImGui.begin("Scenario View", ImGuiWindowFlags.NoBackground);
        float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;

        ThinGLUtils.GLOBAL_RENDER_STACK.pushMatrix();
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(1 / x, 1080F / ThinGL.windowInterface().getFramebufferHeight(), x);
        ThinGLUtils.GLOBAL_RENDER_STACK.translate(ImGui.getWindowPosX(), ImGui.getWindowPosY() + 20, 0);
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(ImGui.getWindowSizeX() / 1920f, (ImGui.getWindowSizeY() - 20) / 1080f, ImGui.getWindowSizeX() / 1920f);
//        if (screen != null) {
//            screen.render(delta);
//        }
        ThinGLUtils.GLOBAL_RENDER_STACK.popMatrix();
        ImGui.end();
    }

    // TODO.
    private void renderMenuBar() {
        ImGui.beginMainMenuBar();

        if (ImGui.beginMenu("Timeline")) {
            if (ImGui.menuItem("Play")) {
                timeline.setPlaying(true);
            }
            if (ImGui.menuItem("Pause", false, timeline.isPlaying())) {
                timeline.setPlaying(false);
            }
            ImGui.endMenu();
        }

        if (ImGui.beginMenu("Edit")) {
            if (ImGui.menuItem("Undo", "Ctrl+Z")) {}
            if (ImGui.menuItem("Redo", "Ctrl+Y", false, false)) {} // Disabled item
            ImGui.separator();
            if (ImGui.menuItem("Cut", "Ctrl+X")) {}
            if (ImGui.menuItem("Copy", "Ctrl+C")) {}
            if (ImGui.menuItem("Paste", "Ctrl+V")) {}
            ImGui.endMenu();
        }

        ImGui.endMainMenuBar();
    }
}
