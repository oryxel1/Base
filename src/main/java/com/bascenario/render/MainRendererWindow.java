package com.bascenario.render;

import com.bascenario.audio.AudioManager;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.render.api.Screen;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.WindowUtil;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.application.GLFWApplicationRunner;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class MainRendererWindow extends GLFWApplicationRunner {
    @Setter
    @Getter
    private Screen currentScreen;

    private Scenario scenario;

    private double mouseX, mouseY;

    private final boolean initFullScreen;

    public MainRendererWindow(Screen screen, boolean initFullScreen) {
        super(new Configuration().setWindowTitle("Blue Archive Scenario Engine").setExtendedDebugMode(true));
        setCurrentScreen(screen);
        this.initFullScreen = initFullScreen;
    }

    public void launch() {
        super.launch();
    }

    @Override
    protected void createWindow() {
        super.createWindow();

        if (this.initFullScreen) {
            WindowUtil.setFullScreen(this.window, true);
        }

        GLFW.glfwSetWindowSizeLimits(this.window, 1280, 899, GLFW_DONT_CARE, GLFW_DONT_CARE);
        GLFW.glfwSetFramebufferSizeCallback(this.window, (window, width, height) -> {
            if (window != this.window) {
                return;
            }

            if (this.currentScreen != null) {
                this.currentScreen.init();
            }
        });
        GLFW.glfwSetCursorPosCallback(this.window, (window, x, y) -> {
            if (window != this.window) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(this.window, (window, button, action, mode) -> {
            if (window != this.window || this.currentScreen == null) {
                return;
            }

            if (action == 1) {
                this.currentScreen.mouseClicked(mouseX, mouseY, button);
            } else {
                this.currentScreen.mouseRelease();
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        FontUtil.loadFonts();
    }

    @Override
    protected void render(Matrix4fStack positionMatrix) {
        ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, this.windowInterface.getFramebufferWidth(), this.windowInterface.getFramebufferHeight(), Color.BLACK);

        this.pollSound();
        if (this.currentScreen != null) {
            if (!this.currentScreen.isInit()) {
                this.currentScreen.init();
                this.currentScreen.setInit(true);
            }

            this.currentScreen.render(positionMatrix, this.windowInterface, this.mouseX, this.mouseY);
        }
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
        if (this.currentScreen instanceof ScenarioScreen screen) {
            this.scenario = screen.getScenario();
        } else if (this.currentScreen instanceof ScenarioPreviewScreen screen) {
            this.scenario = screen.getScenario();
        } else {
            this.scenario = null;
        }
    }

    private final List<Scenario.Sound> playedSounds = new ArrayList<>();
    private Scenario.Sound sound;

    private void pollSound() {
        if (this.scenario == null) {
            return;
        }

        long time = currentScreen instanceof ScenarioScreen screen ? screen.getDuration() : -1;
        if (time != -1 && this.sound != null && this.sound.end() <= time && this.sound.end() > 0) {
            AudioManager.getInstance().stop();
            this.sound = null;
            System.out.println("Stop");
        }

        for (Scenario.Sound sound : this.scenario.getSounds()) {
            if (time == -1 && sound.start() != -1) { // Still in preview, only accept start time -1
                continue;
            } else if (time != -1) {
                if (((ScenarioScreen) currentScreen).getSinceLastDialogue() < sound.start()) {
                    continue;
                }
            }
            if (this.playedSounds.contains(sound)) {
                continue;
            }

            this.playedSounds.add(sound);
            this.sound = sound;
            AudioManager.getInstance().play(new File(this.sound.path()));
            if (sound.end() < 0) {
                AudioManager.getInstance().loop();
            }
        }
    }
}
