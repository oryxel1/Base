package oxy.bascenario.screen;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.Base;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ThinGLUtils;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TitleScreen extends ExtendableScreen {
    @Override
    public void show() {
        super.show();
        Base.instance();
    }

    @Override
    public void render(float delta) {
        ThinGLUtils.start();
        renderLogo();
        ThinGLUtils.end();
    }

    private void renderLogo() {
        final Texture2D logo = Base.instance().assetsManager().texture("assets/base/uis/Blue_Archive.png");
        float logoWidth = 3385 / 5F, logoHeight = 1218 / 5F;
        float logoX = 1920 - logoWidth - 50, logoY = 19.98f;
        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, logo, logoX, logoY, logoWidth, logoHeight);
        TextUtils.textRun(31.95f, TextRun.fromString(FontUtils.SEMI_BOLD, "Scenario Engine"), logoX + logoWidth / 2, logoY + logoHeight);
    }
}
