package oxy.bascenario.api.render.elements.text.font;

import lombok.Builder;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Font(FileInfo file, FontStyle style, FontType type) {
    public static Font DEFAULT = new Font(null, FontStyle.REGULAR, FontType.NotoSans);
}
