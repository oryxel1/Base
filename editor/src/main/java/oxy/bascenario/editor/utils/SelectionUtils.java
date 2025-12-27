package oxy.bascenario.editor.utils;

import imgui.ImVec2;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.screens.renderer.element.SpriteRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class SelectionUtils {
    public static ImVec2 selectionBox(ElementRenderer<?> base) {
        return switch (base) {
            default -> null;
        };
    }
}
