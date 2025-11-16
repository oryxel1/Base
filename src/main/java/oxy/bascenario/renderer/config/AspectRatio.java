package oxy.bascenario.renderer.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AspectRatio {
    FourToThree(4, 3), SixteenToNine(16, 9);

    private final int width, height;
}
