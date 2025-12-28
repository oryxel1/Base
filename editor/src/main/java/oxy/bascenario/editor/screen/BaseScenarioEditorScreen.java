package oxy.bascenario.editor.screen;

import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import lombok.Getter;
import lombok.Setter;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.element.AssetsUI;
import oxy.bascenario.editor.element.ElementAdder;
import oxy.bascenario.editor.element.EventAdder;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.inspector.Inspector;

import oxy.bascenario.screens.renderer.element.SpriteRenderer;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ThinGLUtils;

public class BaseScenarioEditorScreen extends ExtendableScreen {
    @Getter
    protected final Scenario.Builder scenario;
    protected final Timeline timeline;
    protected final ElementAdder elementAdder;
    protected final EventAdder eventAdder;
    protected final Inspector inspector;

    @Getter @Setter
    protected Object dragging;

    public BaseScenarioEditorScreen(Scenario.Builder scenario) {
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

    private long lastUpdate;
    @Override
    public void render(float delta) {
        ImGui.dockSpaceOverViewport(0, new ImGuiViewport(0), ImGuiDockNodeFlags.PassthruCentralNode);

        renderMenuBar();
        timeline.render();
        elementAdder.render();
        eventAdder.render();
        inspector.render();
        AssetsUI.render(timeline, scenario);

        // There are better ways to do this yes, but I'm too fucking lazy.
        if (System.currentTimeMillis() - lastUpdate >= 1000L) {
            new Thread(() -> timeline.updateScenario(false)).start();
            lastUpdate = System.currentTimeMillis();
        }

        ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0, 0, 0, 0);
        ImGui.begin("Scenario View", ImGuiWindowFlags.NoBackground);
        renderScenarioWindow();
        ImGui.end();
    }

    public void update() {
    }

    protected final void renderScenarioWindow() {
        float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
        ThinGLUtils.GLOBAL_RENDER_STACK.pushMatrix();
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(1 / x, 1080F / ThinGL.windowInterface().getFramebufferHeight(), x);
        ThinGLUtils.GLOBAL_RENDER_STACK.translate(ImGui.getWindowPosX(), ImGui.getWindowPosY() + 23, 0);
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(ImGui.getWindowSizeX() / 1920f, (ImGui.getWindowSizeY() - 23) / 1080f, ImGui.getWindowSizeX() / 1920f);
        renderScenario();
        ThinGLUtils.GLOBAL_RENDER_STACK.popMatrix();
    }

    public void renderScenario() {
    }

    // TODO.
    private void renderMenuBar() {
        ImGui.beginMainMenuBar();

        if (ImGui.beginMenu("Timeline")) {
            if (ImGui.menuItem("Play")) {
                setPlaying(true);
            }
            if (ImGui.menuItem("Pause", false, timeline.isPlaying())) {
                setPlaying(false);
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

    protected void setPlaying(boolean playing) {
        timeline.setPlaying(playing);
    }
}
