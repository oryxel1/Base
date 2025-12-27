package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.element.AssetsUI;
import oxy.bascenario.utils.NFDUtils;

public class SpriteInspector {
    private static FileInfo skeleton, atlas;

    public static Sprite render(Sprite sprite) {
        Sprite.Builder builder = sprite.toBuilder();
        if (ImGui.button("xd")) {
            System.out.println(NFDUtils.pickFolder());
        }
//        if (skeleton != null) {
//            builder.skeleton(skeleton);
//            skeleton = null;
//        }
//
//        if (atlas != null) {
//            builder.atlas(atlas);
//            atlas = null;
//        }
//
//        if (sprite.skeleton() != null) {
//            ImGui.textUnformatted("Skeleton: " + sprite.skeleton());
//        }
//        if (sprite.atlas() != null) {
//            ImGui.textUnformatted("Atlas: " + sprite.atlas());
//        }
//
//        AssetsUI.pick("Pick Skeleton", file -> skeleton = file, "*");
//        AssetsUI.pick("Pick Atlas", file -> atlas = file, "*");

        return builder.build();
    }
}
