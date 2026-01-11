package oxy.bascenario.api.render.elements.text.font;

import oxy.bascenario.api.utils.FileInfo;

import java.util.Optional;

public record Font(Optional<FileInfo> info, FontStyle style, FontType type) {
    public static Font DEFAULT = new Font(Optional.empty(), FontStyle.REGULAR, FontType.NotoSans);
}
