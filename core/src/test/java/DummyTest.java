import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.dialogue.QuestionSelectionRenderer;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.thingl.ThinGLUtils;

import java.util.List;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class DummyTest {
    private static final QuestionSelectionRenderer renderer = new QuestionSelectionRenderer();

    public static void main(String[] args) {
        Launcher.launch(new ExtendableScreen() {
            @Override
            public void show() {
                renderer.setValues(FontType.NotoSans, "I mean, what's the point of spreading rumors like this?\n" +
                        "How is that related to the price of crab?",
                        List.of(
                                new ShowQuestionSelectionEvent.Answer(0, "They're probably the kind of people who will profit if the price goes down.", true),
                                new ShowQuestionSelectionEvent.Answer(0, "They're probably the kind of people who will profit if the price goes down.", true),
                                new ShowQuestionSelectionEvent.Answer(0, "They're probably the kind of people who aren't affected by the price", false)
                        )
                );
            }

            @Override
            public void render(float delta) {
                ThinGLUtils.start();
                ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK,
                        Base.instance().assetsManager().texture("BG_ShoppingMall.jpg"),
                        0, 0, 1920, 1080);
                renderer.render(null);
                ThinGLUtils.end();
            }

            @Override
            public void mouseClicked(double mouseX, double mouseY, int button) {
                renderer.mouseClicked(new ScenarioScreen(Scenario.builder().build()), mouseX, mouseY);
            }

            @Override
            public void mouseRelease() {
                renderer.mouseRelease();
            }
        });
    }
}