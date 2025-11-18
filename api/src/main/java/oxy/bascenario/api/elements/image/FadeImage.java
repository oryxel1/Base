package oxy.bascenario.api.elements.image;

import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.utils.FileInfo;

public class FadeImage extends Image {
    private final Fade fadeIn, fadeOut;

    public FadeImage(FileInfo file, Fade fadeIn, Fade fadeOut) {
        super(file);
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    public Fade fadeIn() {
        return fadeIn;
    }

    public Fade fadeOut() {
        return fadeOut;
    }
}
