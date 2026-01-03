package oxy.bascenario;

import oxy.bascenario.screen.TitleScreen;
import oxy.bascenario.utils.Launcher;

public class Main {
    public static void main(String[] args) {
        Launcher.launch(new TitleScreen(), false);
    }
}