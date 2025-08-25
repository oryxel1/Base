import com.bascenario.launcher.Launcher;
import com.bascenario.render.test.ElementTestScreen;

public class NormalRenderTest {
    public static void main(String[] args) {
        Launcher.WINDOW.setCurrentScreen(new ElementTestScreen());
        Launcher.main(args);
    }
}
