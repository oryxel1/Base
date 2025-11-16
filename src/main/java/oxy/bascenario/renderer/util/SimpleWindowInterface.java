package oxy.bascenario.renderer.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;

@RequiredArgsConstructor
public final class SimpleWindowInterface extends WindowInterface {
    @Getter
    private final long windowHandle;

    @Override
    public int getFramebufferWidth() {
        return 1920;
    }

    @Override
    public int getFramebufferHeight() {
        return 1080;
    }

    @Override
    public void free() {
    }
}
