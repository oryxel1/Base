package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.element.AssetsUI;
import oxy.bascenario.utils.ImGuiUtils;

public class ImageInspector {
    private static FileInfo last;

    public static AnimatedImage render(AnimatedImage image) {
        AnimatedImage.Builder builder = image.toBuilder();
        AssetsUI.pick("Pick Gif!", file -> last = file, "gif");
        if (last != null) {
            builder.file(last);
            last = null;
        }

        builder.start(ImGuiUtils.sliderInt("Start Position (ms)", (int) image.start(), 0, 20000));
        builder.loop(ImGuiUtils.checkbox("Loop", image.loop()));

        ImGui.separatorText("");

        builder.color(ImGuiUtils.color("Color", image.color()));
        builder.width(ImGuiUtils.sliderInt("Width", image.width(), 0, 1920));
        builder.height(ImGuiUtils.sliderInt("Height", image.height(), 0, 1080));
        return builder.build();
    }

    public static Image render(Image image) {
        Image.Builder builder = image.toBuilder();
        AssetsUI.pick("Pick Image!", file -> last = file, "png,jpg");
        if (last != null) {
            builder.file(last);
            last = null;
        }

        builder.color(ImGuiUtils.color("Color", image.color()));
        builder.width(ImGuiUtils.sliderInt("Width", image.width(), 0, 1920));
        builder.height(ImGuiUtils.sliderInt("Height", image.height(), 0, 1080));
        return builder.build();
    }
}
