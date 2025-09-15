package com.bascenario.render.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bascenario.Launcher;
import com.bascenario.managers.AudioManager;
import com.bascenario.managers.TextureManager;
import com.bascenario.render.api.Screen;
import com.bascenario.render.main.components.TitleScreenButton;
import com.bascenario.util.MathUtil;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.RenderUtil;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;

public class TitleScreen extends Screen {
    private boolean init;

    private OrthographicCamera camera;
    private TwoColorPolygonBatch batch;

    private SkeletonRenderer renderer;

    private Skeleton skeleton;
    private AnimationState state;

    private TextureAtlas atlas;

    public void init() {
        this.initComponents();

        if (this.init) {
            this.camera.setToOrtho(false);
            return;
        }

        AudioManager.getInstance().playFadeIn("assets/base/musics/title/hiniature.mp3", 800L, true, 0.5F, true);

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.batch = new TwoColorPolygonBatch();
        this.renderer = new SkeletonRenderer();

        this.atlas = new TextureAtlas(Gdx.files.internal("assets/base/uis/title/background/CH0230_home.atlas"));
        SkeletonBinary json = new SkeletonBinary(atlas);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("assets/base/uis/title/background/CH0230_home.skel"));

        this.skeleton = new Skeleton(skeletonData);

        this.skeleton.setPosition(0, 0);
        this.state = new AnimationState(new AnimationStateData(skeletonData));

        this.state.addAnimation(0, "Idle_01", true, 0);
        this.init = true;
    }

    @Override
    public void dispose() {
        AudioManager.getInstance().stopFadeOut("assets/base/musics/title/hiniature.mp3", 800L);

        this.atlas.dispose();
    }

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        // Render hina home sprite :D (background)!
        final float delta = Gdx.graphics.getDeltaTime();
        this.state.update(delta);
        this.state.apply(this.skeleton);

        float scale = 0.00038F * ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2f);

        this.skeleton.setScale(scale, scale);
        this.skeleton.update(delta);
        this.skeleton.updateWorldTransform();

        this.camera.position.set((-1 / 100F) * window.getFramebufferWidth(), (-50F / 100) * -window.getFramebufferHeight(), 0);
        this.camera.update();
        this.batch.getProjectionMatrix().set(camera.combined);

        this.batch.begin();
        this.renderer.draw(this.batch, this.skeleton);
        this.batch.end();

        RenderUtil.render(() -> {
            this.renderThinGL(positionMatrix, window, mouseX, mouseY);
            super.render(positionMatrix, window, mouseX, mouseY);
        });
    }

    private void renderThinGL(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        final float windowWidth = window.getFramebufferWidth(), windowHeight = window.getFramebufferHeight();

        final Texture2D logo = TextureManager.getInstance().getTexture("/assets/base/uis/Blue_Archive.png");
        float logoWidth = ((3385 / 5F) / 1920F) * windowWidth;
        float logoHeight = ((1218 / 5F) / 1080F) * windowHeight;
        float logoX = window.getFramebufferWidth() - logoWidth - (0.02604F * windowWidth);
        float logoY = 0.0185F * windowHeight;
        ThinGL.renderer2D().texture(positionMatrix, logo, logoX, logoY, logoWidth, logoHeight);

        ThinGL.rendererText().textRun(
                positionMatrix,
                TextRun.fromString(FontUtil.getFont("NotoSansSemiBold", MathUtil.floor(0.0213F * ((windowWidth + windowHeight) / 2F))), "Scenario Engine"),
                logoX + logoWidth / 2, logoY + logoHeight
        );
    }

    private void initComponents() {
        final WindowInterface window = ThinGL.windowInterface();
        final float windowWidth = window.getFramebufferWidth(), windowHeight = window.getFramebufferHeight();

        float logoWidth = ((3385 / 5F) / 1920F) * windowWidth;
        float logoHeight = ((1218 / 5F) / 1080F) * windowHeight;
        float logoX = window.getFramebufferWidth() - logoWidth - (0.02604F * windowWidth);
        float logoY = 0.0185F * windowHeight;

        float textHeightY = ThinGL.rendererText().getExactHeight(TextRun.fromString(FontUtil.getFont("NotoSansSemiBold", MathUtil.floor(0.0213F * ((windowWidth + windowHeight) / 2F))), "Scenario Engine").shape());

        float posY = logoY + logoHeight + textHeightY + 0.04629629629F * windowHeight;
        this.components.clear();
        this.components.add(new TitleScreenButton("Play Scenario", logoX, posY, logoWidth, 0.08F * windowHeight, () -> Launcher.WINDOW.setCurrentScreen(new ScenarioListScreen())));
        posY += 0.08F * windowHeight + 0.04629629629F * windowHeight;
        this.components.add(new TitleScreenButton("Scenario Editor", logoX, posY, logoWidth, 0.08F * windowHeight, () -> {
        }));
        posY += 0.08F * windowHeight + 0.04629629629F * windowHeight;
        this.components.add(new TitleScreenButton("Settings", logoX, posY, logoWidth, 0.08F * windowHeight, () -> {
        }));
    }
}
