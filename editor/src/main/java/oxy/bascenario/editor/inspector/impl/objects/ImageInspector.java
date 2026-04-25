package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.event.PopupEvent;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.miniuis.AssetsUI;
import oxy.bascenario.utils.ImGuiUtils;

public class ImageInspector {
    private static FileInfo last, last1, last2;

    public static PopupEvent render(PopupEvent event) {
        final PopupEvent.Builder builder = event.toBuilder();
        PopupEvent.Type type = PopupEvent.Type.values()[ImGuiUtils.combo("Type", event.type().ordinal(), PopupEvent.Type.getAlls())];
        builder.type(type);
        if (type == PopupEvent.Type.SET) {
            AssetsUI.pick("Pick Image!", file -> last2 = file, "png,jpg");
            if (last2 != null) {
                builder.popup(last2);
                last2 = null;
            }
        }

        return builder.build();
    }

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
        AssetsUI.pick("Pick Image!", file -> last1 = file, "png,jpg");
        if (last1 != null) {
            builder.file(last1);
            last1 = null;
        }

        builder.color(ImGuiUtils.color("Color", image.color()));
        builder.width(ImGuiUtils.sliderInt("Width", image.width(), 0, 1920));
        builder.height(ImGuiUtils.sliderInt("Height", image.height(), 0, 1080));
        return builder.build();
    }
}
