package oxy.base.editor.inspector.impl.objects;

import oxy.base.api.render.elements.emoticon.Emoticon;
import oxy.base.api.render.elements.emoticon.EmoticonType;
import oxy.base.utils.ImGuiUtils;

public class EmoticonInspector {
    public static Emoticon render(Emoticon emoticon) {
        final Emoticon.Builder builder = emoticon.toBuilder();
        builder.type(EmoticonType.values()[ImGuiUtils.combo("Type", emoticon.type().ordinal(), EmoticonType.getAlls())]);
        builder.sound(ImGuiUtils.checkbox("Play SFX", emoticon.sound()));
        return builder.build();
    }
}
