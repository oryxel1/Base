package oxy.bascenario;

import oxy.bascenario.screen.title.TitleScreen;
import oxy.bascenario.utils.Launcher;

public class Main {
    public static void main(String[] args) {
        Launcher.launch(TitleScreen.INSTANCE, false);
    }
}