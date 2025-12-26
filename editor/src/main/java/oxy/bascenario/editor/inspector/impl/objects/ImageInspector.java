package oxy.bascenario.editor.inspector.impl.objects;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.utils.ImGuiUtils;

public class ImageInspector {
//    public static RendererImage render(Scenario.Builder scenario, RendererImage image) {
//        RendererImage.RendererImageBuilder builder = image.toBuilder();
//        builder.image(render(scenario, image.image()));
//        builder.color(ImGuiUtils.color("Color", image.color()));
//        builder.width(ImGuiUtils.sliderInt("Width", image.width(), 0, 1920));
//        builder.height(ImGuiUtils.sliderInt("Width", image.height(), 0, 1080));
//        return builder.build();
//    }
//
//    public static Image render(Scenario.Builder scenario, Image image) {
//        if (image instanceof AnimatedImage animatedImage) {
//            AnimatedImage.AnimatedImageBuilder animatedBuilder = animatedImage.toBuilder();
//            ImGuiUtils.pick(animatedBuilder::file, scenario, "File", false, "png,jpg,gif");
//
//            animatedBuilder.start(ImGuiUtils.sliderInt("Start Position (ms)", (int) animatedImage.getStart(), 0, 20000));
//            animatedBuilder.loop(ImGuiUtils.checkbox("Loop", animatedImage.isLoop()));
//
//            return animatedBuilder.build();
//        }
//
//        final Image.ImageBuilder builder = image.toBuilder();
//        ImGuiUtils.pick(builder::file, scenario, "File", false, "png,jpg");
//        return builder.build();
//    }
}
