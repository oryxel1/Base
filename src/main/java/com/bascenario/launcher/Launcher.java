package com.bascenario.launcher;

import com.bascenario.render.MainRendererWindow;
import com.bascenario.render.api.Screen;

public class Launcher {
    public static MainRendererWindow WINDOW;

    public static void launch(Screen screen) {
        WINDOW = new MainRendererWindow(screen);
        WINDOW.launch();
    }
}
