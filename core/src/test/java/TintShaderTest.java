import net.lenni0451.commons.color.Color;
import oxy.base.Base;
import oxy.base.utils.ExtendableScreen;
import oxy.base.utils.Launcher;
import oxy.base.utils.thingl.ThinGLExtended;
import oxy.base.utils.thingl.ThinGLUtils;

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