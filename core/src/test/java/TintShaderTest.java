import net.lenni0451.commons.color.Color;
import oxy.bascenario.Base;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.thingl.ThinGLExtended;
import oxy.bascenario.utils.thingl.ThinGLUtils;

public class TintShaderTest {
    public static void main(String[] args) {
        Launcher.launch(new ExtendableScreen() {
            @Override
            public void show() {
            }

            @Override
            public void render(float delta) {
                ThinGLUtils.start();

                ThinGLExtended.get().getPrograms().getNightVision().bindInput();
                ThinGLUtils.renderBackground(Base.instance().assetsManager().texture("BG_ShoppingMall.jpg"), Color.WHITE);
                ThinGLExtended.get().getPrograms().getNightVision().unbindInput();
                ThinGLExtended.get().getPrograms().getNightVision().renderFullscreen();
                ThinGLExtended.get().getPrograms().getNightVision().clearInput();

                ThinGLUtils.end();
            }
        }, false);
    }
}