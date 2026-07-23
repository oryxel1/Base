package oxy.base;

import oxy.base.screen.SplashScreen;
import oxy.base.utils.Launcher;

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