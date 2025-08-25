package com.bascenario.launcher;

import com.bascenario.render.MainRendererWindow;
import imgui.app.Application;

public class Launcher {
    public static MainRendererWindow WINDOW = new MainRendererWindow();

    public static void main(String[] args) {
        Application.launch(WINDOW);
    }
}
