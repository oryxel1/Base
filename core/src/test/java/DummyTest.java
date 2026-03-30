import imgui.ImGui;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.Base;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.thingl.ThinGLUtils;
import oxy.bascenario.utils.thingl.other.TilingSprite;

public class DummyTest {
    public static void main(String[] args) {
        Launcher.launch(new ExtendableScreen() {
            private TilingSprite sprite;
            @Override
            public void show() {
                sprite = new TilingSprite(Base.instance().assetsManager().texture("FX_TEX_Smoke_Scroll_23.png"));
                sprite.scale = 4;
                sprite.color = 0x4c413f;
                sprite.setTilePosition(0, 240);
                sprite.rotation = 0.55f;
            }

            @Override
            public void render(float delta) {
                ThinGLUtils.start();

                sprite.width = 2000;
                sprite.height = 512;

                sprite.render(0, 1080 - 512 * 1.6f);
                sprite.addTileOffset(1, 0);

                ThinGLUtils.end();
            }
        }, false);
    }
}
