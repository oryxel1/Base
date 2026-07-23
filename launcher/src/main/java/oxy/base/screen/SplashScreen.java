package oxy.base.screen;

import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.text.TextRun;
import oxy.base.screen.title.TitleScreen;
import oxy.base.utils.ExtendableScreen;
import oxy.base.utils.Launcher;
import oxy.base.utils.thingl.ThinGLUtils;
import oxy.base.utils.font.FontUtils;
import oxy.base.utils.font.TextUtils;

import static oxy.base.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

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
