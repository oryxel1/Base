import com.bascenario.sprite.Sprite;
import net.raphimc.thingl.implementation.application.GLFWApplicationRunner;
import org.joml.Matrix4fStack;

import java.io.File;

public class SpriteParser extends GLFWApplicationRunner {
    public SpriteParser() {
        super(new Configuration());
        this.launch();
    }

    public static void main(String[] args) {
        new SpriteParser();
    }

    @Override
    protected void init() {
        super.init();
        Sprite.build("Test", new File("C:\\Users\\PC\\Downloads\\hina_spr.atlas"), new File("C:\\Users\\PC\\Downloads\\hina_spr.png"));
    }

    @Override
    protected void render(Matrix4fStack positionMatrix) {

    }
}
