package com.bascenario.launcher;

import com.bascenario.render.MainRendererWindow;
import imgui.app.Application;

public class Launcher {
    public static void main(String[] args) {
        Application.launch(new MainRendererWindow());
    }
}
