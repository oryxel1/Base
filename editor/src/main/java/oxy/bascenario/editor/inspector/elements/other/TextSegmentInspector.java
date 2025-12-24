package oxy.bascenario.editor.inspector.elements.other;

import imgui.ImGui;
import imgui.type.ImBoolean;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TextSegmentInspector {
    public static List<TextSegment> render(List<TextSegment> segments) {
        final List<TextSegment> list = new ArrayList<>();
        for (TextSegment segment : segments) {
            final ImBoolean imBoolean = new ImBoolean(true);
            if (ImGui.collapsingHeader("Text Segment##" + ImGuiUtils.COUNTER++, imBoolean)) {
                segment = render(segment);
            }

            if (imBoolean.get()) {
                list.add(segment);
            }
        }

        return list;
    }

    public static TextSegment render(TextSegment segment) {
        TextSegment.Builder builder = segment.toBuilder();
        builder.text(ImGuiUtils.inputMultiLineText("Text", segment.text()));
        builder.type(FontType.values()[ImGuiUtils.combo("Font Type", segment.type().ordinal(), FontType.getAlls())]);
        builder.color(ImGuiUtils.color("Text Color", segment.color()));

        boolean shadow = ImGuiUtils.checkbox("Shadow", segment.styles().contains(TextStyle.SHADOW));
        boolean bold = ImGuiUtils.checkbox("Bold", segment.styles().contains(TextStyle.BOLD));
        boolean italic = ImGuiUtils.checkbox("Italic", segment.styles().contains(TextStyle.ITALIC));
        boolean underline = ImGuiUtils.checkbox("Underline", segment.styles().contains(TextStyle.UNDERLINE));
        boolean strikethrough = ImGuiUtils.checkbox("Strikethrough", segment.styles().contains(TextStyle.STRIKETHROUGH));
        final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);
        if (shadow) styles.add(TextStyle.SHADOW);
        if (bold) styles.add(TextStyle.BOLD);
        if (italic) styles.add(TextStyle.ITALIC);
        if (underline) styles.add(TextStyle.UNDERLINE);
        if (strikethrough) styles.add(TextStyle.STRIKETHROUGH);
        builder.styles().clear();
        builder.styles().addAll(styles);

        return builder.build();
    }
}
