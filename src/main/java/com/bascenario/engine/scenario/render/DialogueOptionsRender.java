package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.Scenario;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class DialogueOptionsRender {
    private final Scenario.DialogueOptions dialogueOptions;

    public void render(Matrix4fStack positionMatrix, WindowInterface window) {
        System.out.println("Dialogue options!");
    }
}
