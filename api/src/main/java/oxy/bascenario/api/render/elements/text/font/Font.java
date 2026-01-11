package oxy.bascenario.api.render.elements.text.font;

import lombok.Builder;
import oxy.bascenario.api.utils.FileInfo;

import java.util.Optional;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Font(Optional<FileInfo> file, FontStyle style, FontType type) {
    public static Font DEFAULT = new Font(Optional.empty(), FontStyle.REGULAR, FontType.NotoSans);
}
