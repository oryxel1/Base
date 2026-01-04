package oxy.bascenario.screen;

import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.screen.title.TitleScreen;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.ThinGLUtils;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class SplashScreen extends ExtendableScreen {
    @Setter
    private boolean done;

    @Override
    public void render(float delta) {
        ThinGLUtils.start();
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGB(15, 15, 15));

        final TextRun run = TextRun.fromString(FontUtils.DEFAULT, "If you see this screen, that means this took a bit to load, So you just gotta wait!");
        TextUtils.textRun(40, run, 1920 / 2f - TextUtils.getVisualWidth(40, run.shape()) / 2, 1080 / 2f - TextUtils.getVisualHeight(40, run.shape()) / 2);
        ThinGLUtils.end();

        if (done) {
            Launcher.WINDOW.setScreen(TitleScreen.INSTANCE);
        }
    }
}
