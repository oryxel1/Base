package oxy.bascenario.api.render.elements.image;

import lombok.Getter;
import oxy.bascenario.api.utils.FileInfo;

@Getter
public class AnimatedImage extends Image {
    private final boolean loop;

    public AnimatedImage(FileInfo file, boolean loop) {
        super(file);
        this.loop = loop;
    }
}
