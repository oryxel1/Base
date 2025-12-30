import imgui.ImGui;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;

public class ImGuiTest {
    public static void main(String[] args) {
        Launcher.launch(new ExtendableScreen() {
            @Override
            public void render(float delta) {
                ImGui.showDemoWindow();
            }
        }, false);
    }
}
