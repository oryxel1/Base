import imgui.ImGui;
import oxy.base.utils.ExtendableScreen;
import oxy.base.utils.Launcher;

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
