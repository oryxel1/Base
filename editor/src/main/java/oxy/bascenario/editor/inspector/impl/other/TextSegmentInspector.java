package oxy.bascenario.editor.inspector.impl.other;

import imgui.ImGui;
import imgui.type.ImBoolean;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.miniuis.AssetsUI;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TextSegmentInspector {
    public static List<TextSegment> render(Scenario.Builder scenario, List<TextSegment> segments) {
        final List<TextSegment> list = new ArrayList<>();
        for (TextSegment segment : segments) {
            final ImBoolean imBoolean = new ImBoolean(true);
            if (ImGui.collapsingHeader("Text Segment##" + ImGuiUtils.COUNTER++, imBoolean)) {
                segment = render(scenario, segment);
            }

            if (imBoolean.get()) {
                list.add(segment);
            }
        }

        return list;
    }

    private static FileInfo last;
    public static TextSegment render(Scenario.Builder scenario, TextSegment segment) {
        TextSegment.Builder builder = segment.toBuilder();
        builder.text(ImGuiUtils.inputMultiLineText("Text", segment.text()));

        if (ImGuiUtils.checkbox("Custom Font", segment.font().isPresent())) {
            if (segment.font().isEmpty()) {
                // We kinda need to set this to something first.
                builder.font(new FileInfo("assets/base/fonts/global/NotoSans-Regular.ttf", false, true));
            }

            AssetsUI.pick("Pick Font!", file -> last = file, "ttf");
            ImGui.sameLine();
            ImGui.textUnformatted(segment.font().isEmpty() ? "" : segment.font().get().path());
        } else {
            builder.font(null);
            builder.type(FontType.values()[ImGuiUtils.combo("Font Type", segment.type().ordinal(), FontType.getAlls())]);
        }

        if (last != null) {
            builder.font(last);
            last = null;
        }

        ImGui.separatorText("");

        builder.color(ImGuiUtils.color("Text Color", segment.color()));

        if (ImGuiUtils.checkbox("Outline", segment.outline().isPresent())) {
            builder.outline(ImGuiUtils.color("Outline Color", segment.outline().isEmpty() ? Color.WHITE : segment.outline().get()));
        } else {
            builder.outline(null);
        }

        ImGui.separatorText("");

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
