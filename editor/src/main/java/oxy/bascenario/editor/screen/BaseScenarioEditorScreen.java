package oxy.bascenario.editor.screen;

import com.badlogic.gdx.Screen;
import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiWindowFlags;
import lombok.Getter;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.miniuis.AssetsUI;
import oxy.bascenario.editor.miniuis.ObjectsUI;
import oxy.bascenario.editor.miniuis.ActionsUI;
import oxy.bascenario.editor.timeline.Timeline;
import oxy.bascenario.editor.inspector.Inspector;

import oxy.bascenario.editor.utils.TrackParser;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.thingl.ThinGLUtils;

public class BaseScenarioEditorScreen extends ExtendableScreen {
    private final Screen prevScreen;

    @Getter
    protected final Scenario.Builder scenario;
    protected final Timeline timeline;
    protected final ObjectsUI objectsUI;
    protected final ActionsUI eventAdder;
    protected final Inspector inspector;

    public BaseScenarioEditorScreen(Screen prevScreen, Scenario.Builder scenario) {
        this.prevScreen = prevScreen;
        this.scenario = scenario;
        this.timeline = new Timeline(this, scenario);
        this.objectsUI = new ObjectsUI(this, this.timeline);
        this.eventAdder = new ActionsUI(this, this.timeline);
        this.inspector = new Inspector(this, this.timeline);
    }

    @Override
    public void render(float delta) {
        ImGui.dockSpaceOverViewport(0, new ImGuiViewport(0), ImGuiDockNodeFlags.PassthruCentralNode);

        renderMenuBar();
        timeline.render();
        objectsUI.render();
        eventAdder.render();
        inspector.render();
        AssetsUI.render(timeline, scenario);

        ImGui.getStyle().setColor(ImGuiCol.WindowBg, 0, 0, 0, 0);
        ImGui.begin("Scenario View", ImGuiWindowFlags.NoBackground);
        renderScenarioWindow();
        ImGui.end();

        boolean control = ImGui.isKeyDown(ImGuiKey.RightCtrl) || ImGui.isKeyDown(ImGuiKey.LeftCtrl);
        if ((control) && ImGui.isKeyReleased(ImGuiKey.Z)) {
            timeline.undo();
        }
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
            if (ImGui.menuItem("Undo", "Ctrl+Z", false, timeline.canUndo())) {
                timeline.undo();
            }
            ImGui.endMenu();
        }

        if (ImGui.beginMenu("Save")) {
            boolean asJson;
            if ((asJson = ImGui.menuItem("As Json")) || ImGui.menuItem("As Binary")) {
                scenario.saveType(asJson ? Scenario.SaveType.JSON : Scenario.SaveType.BINARY);
                save();
            }
            ImGui.endMenu();
        }

        if (ImGui.beginMenu("Exit")) {
            if (ImGui.menuItem("Exit and Save")) {
                save();
                Launcher.WINDOW.setScreen(prevScreen);
            }
            if (ImGui.menuItem("Exit and Discard")) {
                confirmExitAndDiscard = true;
            }
            ImGui.endMenu();
        }

        ImGui.endMainMenuBar();

        if (confirmExitAndDiscard) {
            ImGui.openPopup("AreYouSureeeeeee");
            confirmExitAndDiscard = false;
        }

        if (ImGui.beginPopupModal("AreYouSureeeeeee", ImGuiWindowFlags.NoResize)) {
            ImGui.text("You rlly want to discard all that hard works? Are you sureeeeee?");
            if (ImGui.button("Nevermine let's save and quit!")) {
                save();
                Launcher.WINDOW.setScreen(prevScreen);
            }
            ImGui.sameLine();
            if (ImGui.button("Yep!")) {
                Launcher.WINDOW.setScreen(prevScreen);
            }
            ImGui.endPopup();
        }
    }

    private boolean confirmExitAndDiscard;

    public void setPlaying(boolean playing) {
        timeline.setPlaying(playing);
    }

    private void save() {
        scenario.timestamps().clear();
        scenario.timestamps().addAll(TrackParser.parse(this.timeline.getObjects()));

        Base.instance().scenarioManager().put(scenario.name(), scenario.build());
        try {
            Base.instance().scenarioManager().saveToPath(scenario.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
