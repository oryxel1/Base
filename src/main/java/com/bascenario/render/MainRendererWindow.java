package com.bascenario.render;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.bascenario.audio.AudioManager;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.render.api.Screen;
import com.bascenario.util.WindowUtil;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.RenderUtil;
import lombok.Getter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import net.raphimc.thingl.wrapper.GLStateManager;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainRendererWindow extends ApplicationAdapter {
    @Getter
    private Screen currentScreen;

    private Scenario scenario;

    private double mouseX, mouseY;

    private final boolean fullscreen;
    public MainRendererWindow(Screen screen, boolean fullscreen) {
        this.fullscreen = fullscreen;
        setCurrentScreen(screen);
    }

    @Override
    public void create() {
        long windowHandle = ((Lwjgl3Graphics)Gdx.graphics).getWindow().getWindowHandle();
        // Fix libgdx full screen lol.
        if (this.fullscreen) {
            WindowUtil.setFullScreen(windowHandle, true);
        }

        new ThinGL(new GLFWWindowInterface(windowHandle)) {
            @Override
            protected GLStateManager createGLStateManager() {
                return new GLStateManager();
            }
        };
        ThinGL.config().setRestoreVertexArrayBinding(true);
        ThinGL.config().setRestoreProgramBinding(true);

        ThinGL.windowInterface().addFramebufferResizeCallback((width, height) -> {
            if (this.currentScreen != null) {
                this.currentScreen.init();
            }
        });
        GLFW.glfwSetCursorPosCallback(windowHandle, (window, x, y) -> {
            if (window != windowHandle) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
            if (window != windowHandle || this.currentScreen == null) {
                return;
            }

            if (action == 1) {
                this.currentScreen.mouseClicked(mouseX, mouseY, button);
            } else {
                this.currentScreen.mouseRelease();
            }
        });
//
        FontUtil.loadFonts();
    }

    @Override
    public void render() {
        final Matrix4fStack positionMatrix = new Matrix4fStack(8);

        RenderUtil.render(() -> ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.BLACK));

        ThinGL.get().onFrameBegin();
        ThinGL.get().onFrameStart();

        if (this.currentScreen != null) {
            if (!this.currentScreen.isInit()) {
                this.currentScreen.init();
                this.currentScreen.setInit(true);
            }

            this.currentScreen.render(positionMatrix, ThinGL.windowInterface(), this.mouseX, this.mouseY);
        }

        ThinGL.get().onFrameFinished();
        ThinGL.get().onFrameEnd();

        this.pollSound();
    }

    @Override
    public void dispose() {
        if (this.currentScreen != null) {
            this.currentScreen.dispose();
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

        long time = this.currentScreen instanceof ScenarioScreen screen ? screen.getRealDuration() : -1;
        if (time != -1 && this.sound != null && this.sound.end() <= time && this.sound.end() > 0) {
            AudioManager.getInstance().stop();
            this.sound = null;
        }

        for (Scenario.Sound sound : this.scenario.getSounds()) {
            if (time == -1 && sound.start() != -1) { // Still in preview, only accept start time -1
                continue;
            } else if (time != -1) {
                if (((ScenarioScreen) this.currentScreen).getDuration() < sound.start()) {
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
