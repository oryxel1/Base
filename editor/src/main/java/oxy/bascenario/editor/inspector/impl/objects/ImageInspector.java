package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.utils.ImGuiUtils;

public class ImageInspector {
    private static FileInfo last;

    public static RendererImage<AnimatedImage> renderAnimated(Scenario.Builder scenario, RendererImage<AnimatedImage> image) {
        RendererImage.Builder<AnimatedImage> builder = image.toBuilder();
        ImGuiUtils.pick(file -> last = file, scenario, "Pick Gif!", false, "gif");

        AnimatedImage.Builder builder1 = image.image().toBuilder();

        if (last != null) {
            builder1.file(last);
            last = null;
        }

        builder1.start(ImGuiUtils.sliderInt("Start Position (ms)", (int) image.image().start(), 0, 20000));
        builder1.loop(ImGuiUtils.checkbox("Loop", image.image().loop()));

        builder.image(builder1.build());

        ImGui.separatorText("");

        builder.color(ImGuiUtils.color("Color", image.color()));
        builder.width(ImGuiUtils.sliderInt("Width", image.width(), 0, 1920));
        builder.height(ImGuiUtils.sliderInt("Height", image.height(), 0, 1080));
        return builder.build();
    }

    public static RendererImage<FileInfo> render(Scenario.Builder scenario, RendererImage<FileInfo> image) {
        RendererImage.Builder<FileInfo> builder = image.toBuilder();
        ImGuiUtils.pick(file -> last = file, scenario, "Pick Image!", false, "png,jpg");
        if (last != null) {
            builder.image(last);
            last = null;
        }

        builder.color(ImGuiUtils.color("Color", image.color()));
        builder.width(ImGuiUtils.sliderInt("Width", image.width(), 0, 1920));
        builder.height(ImGuiUtils.sliderInt("Width", image.height(), 0, 1080));
        return builder.build();
    }
}
