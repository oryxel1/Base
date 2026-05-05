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
            private TilingSprite sprite, sprite1;
            @Override
            public void show() {
                sprite = new TilingSprite(Base.instance().assetsManager().texture("FX_TEX_Smoke_Scroll_23.png"));
                sprite.scale = 4;
                sprite.color = 0x4c413f;
                sprite.setTilePosition(0, 240);
                sprite.rotation = 0.55f;

                sprite1 = new TilingSprite(Base.instance().assetsManager().texture("FX_TEX_Smoke_Scroll_23.png"));

                sprite1.scale = 4;
                sprite1.color = 0x4c413f;
                sprite1.setTilePosition(0, 240);
                sprite1.rotation = -0.35f;

                sprite1.width = 2500;
                sprite1.height = 800;

                sprite.width = 2000;
                sprite.height = 512;
            }

            @Override
            public void render(float delta) {
                ThinGLUtils.start();

                sprite1.render(-400, 1080 - 100 * 1.6f);
                sprite1.addTileOffset(1, 0);

                sprite.render(0, 1080 - 512 * 1.6f);
                sprite.addTileOffset(1, 0);

                ThinGLUtils.end();
            }
        }, false);
    }
}