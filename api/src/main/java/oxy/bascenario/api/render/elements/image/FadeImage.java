package oxy.bascenario.api.render.elements.image;

import oxy.bascenario.api.utils.FileInfo;

public class FadeImage extends Image {
    private final int fadeIn, fadeOut;

    public FadeImage(FileInfo file, int fadeIn, int fadeOut) {
        super(file);
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    public int fadeIn() {
        return fadeIn;
    }

    public int fadeOut() {
        return fadeOut;
    }
}
