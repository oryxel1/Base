import imgui.ImGui;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.thingl.ThinGLUtils;

public class DummyTest {
    public static void main(String[] args) {
        Launcher.launch(new ExtendableScreen() {
            @Override
            public void render(float delta) {
                ImGui.showDemoWindow();

                ThinGLUtils.start();

                ThinGLUtils.renderTriangleRectangle(100, 100, 158, 70, 15, 2, Color.WHITE);
//                float x = 100, y = 100;
//                ThinGL.glStateStack().push();
//                ThinGL.glStateStack().disable(GL11C.GL_CULL_FACE);
//                ThinGL.programs().getOutline().bindInput();
//                ThinGL.renderer2D().filledTriangle(GLOBAL_RENDER_STACK, x + 158, y, x + 158, y + 70, x + 158 + 15, y, Color.WHITE.withAlphaF(0.5f));
//                ThinGL.renderer2D().filledTriangle(GLOBAL_RENDER_STACK, x - 15, y + 70, x, y, x, y + 70, Color.WHITE.withAlphaF(0.5f));
//                ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, x, y, x + 158, y + 70, Color.WHITE.withAlphaF(0.5f));
//                ThinGL.programs().getOutline().unbindInput();
//                ThinGL.programs().getOutline().configureParameters(2);
//                ThinGL.programs().getOutline().renderFullscreen();
//                ThinGL.programs().getOutline().renderInput();
//                ThinGL.programs().getOutline().clearInput();
//                ThinGL.glStateStack().pop();

                ThinGLUtils.end();
            }
        }, false);
    }
}
