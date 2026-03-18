import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;

public class RainParticleTest extends ExtendableScreen {
    private PolygonSpriteBatch batch;

    public static void main(String[] args) {
        Launcher.launch(new RainParticleTest());
    }

    ParticleEffect effect = new ParticleEffect();
    @Override
    public void show() {
        effect.load(Gdx.files.internal("test/rain.p"), Gdx.files.internal("test/"));
        effect.start();
        batch = new PolygonSpriteBatch();
        effect.setPosition(200, Gdx.graphics.getHeight() + 50);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        effect.draw(batch, delta);
        batch.end();
    }
}
