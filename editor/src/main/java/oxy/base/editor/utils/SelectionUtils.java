package oxy.base.editor.utils;

import imgui.ImVec2;
import oxy.base.screens.renderer.element.base.ElementRenderer;

public class SelectionUtils {
    public static ImVec2 selectionBox(ElementRenderer<?> base) {
        return switch (base) {
            default -> null;
        };
    }
}
