package oxy.bascenario;

import oxy.bascenario.screen.SplashScreen;
import oxy.bascenario.utils.Launcher;

public class Main {
    public static void main(String[] args) {
        final SplashScreen screen = new SplashScreen();
        new Thread(() -> {
            Base.instance();
            screen.setDone(true);
        }).start();

        Launcher.launch(screen, false, true);
    }
}