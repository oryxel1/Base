package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.miniuis.AssetsUI;

public class SpriteInspector {
    private static FileInfo skeleton, atlas;

    public static Sprite render(Sprite sprite) {
        Sprite.Builder builder = sprite.toBuilder();
        if (skeleton != null) {
            builder.skeleton(skeleton);
            skeleton = null;
        }

        if (atlas != null) {
            builder.atlas(atlas);
            atlas = null;
        }

        AssetsUI.pick("Pick Skeleton", file -> skeleton = file, "skel");
        AssetsUI.pick("Pick Atlas", file -> atlas = file, "atlas");

        if (sprite.skeleton() != null) {
            ImGui.textUnformatted("Skeleton: " + sprite.skeleton());
        }
        if (sprite.atlas() != null) {
            ImGui.textUnformatted("Atlas: " + sprite.atlas());
        }

        return builder.build();
    }
}
