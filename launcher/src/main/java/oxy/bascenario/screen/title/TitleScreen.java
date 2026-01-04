package oxy.bascenario.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.Base;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screen.ScenarioListScreen;
import oxy.bascenario.screen.title.button.TitleScreenButton;
import oxy.bascenario.utils.*;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TitleScreen extends ExtendableScreen {
    public static final TitleScreen INSTANCE = new TitleScreen();

    private OrthographicCamera camera;
    private PolygonSpriteBatch batch;
    private SkeletonRenderer renderer;

    private TextureAtlas atlas;
    private Skeleton skeleton;
    private AnimationState state;

    private final List<TitleScreenButton> buttons = new ArrayList<>();

    private int id;
    @Override
    public void show() {
        if (this.camera != null) {
            return;
        }

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.batch = new PolygonSpriteBatch();

        this.renderer = new SkeletonRenderer();

        this.atlas = new TextureAtlas(FileUtils.toHandle(null, new FileInfo("assets/base/uis/title/CH0086_home.atlas", false, true)));
        SkeletonData skeletonData = new SkeletonBinary(this.atlas).readSkeletonData(Gdx.files.internal("assets/base/uis/title/CH0086_home.skel"));
        this.skeleton = new Skeleton(skeletonData);
        this.state = new AnimationState(new AnimationStateData(skeletonData));
        this.state.setAnimation(0, "Start_Idle_01", false);
        this.state.addAnimation(0, "Idle_01", true, this.state.getTracks().get(0).getTrackComplete());
        this.state.setAnimation(1, "Snow_00_R", true);

        Sound sound = Sound.sound(new FileInfo("assets/base/sounds/Track_67_KARUT_Someday,_sometime.ogg", false, true), true);
        id = sound.id();
        AudioManager.getInstance().play(sound, 1000L);

        initComponents();
    }

    private void initComponents() {
        float logoWidth = 3385 / 5F, logoHeight = 1218 / 5F;
        float logoX = 1920 - logoWidth - 50, logoY = 19.98f;

        float textHeightY = TextUtils.getVisualHeight(31.95f, TextRun.fromString(FontUtils.SEMI_BOLD, "Scenario Engine").shape());

        float posY = logoY + logoHeight + textHeightY + 0.04629629629F * 1080;
        this.buttons.clear();
        this.buttons.add(new TitleScreenButton("Scenario List", logoX, posY, logoWidth, 0.08F * 1080, () -> Launcher.WINDOW.setScreen(new ScenarioListScreen())));
        posY += 0.08F * 1080 + 0.04629629629F * 1080;
        this.buttons.add(new TitleScreenButton("Settings", logoX, posY, logoWidth, 0.08F * 1080, () -> {
        }));
//        posY += 0.08F * 1080 + 0.04629629629F * 1080;
//        this.buttons.add(new TitleScreenButton("Settings", logoX, posY, logoWidth, 0.08F * 1080, () -> {
//        }));
    }

    @Override
    public void resize(int width, int height) {
        if (this.camera != null) {
            this.camera.setToOrtho(false);
        }
    }

    @Override
    public void render(float delta) {
        this.state.update(Gdx.graphics.getDeltaTime());
        updateSkeleton(this.skeleton);

        final WindowInterface window = ThinGL.windowInterface();
        this.camera.position.set((-1 / 100F) * window.getFramebufferWidth(), (-50F / 100) * -window.getFramebufferHeight(), 0);
        this.camera.up.set(0, 1, 0);
        this.camera.direction.set(0, 0, -1);

        this.camera.update();
        this.batch.getProjectionMatrix().set(camera.combined);

        this.batch.begin();
        this.renderer.draw(this.batch, this.skeleton);
        this.batch.end();

        ThinGLUtils.start();
        if (this.state.getTracks().get(0).getAnimation().getName().equals("Idle_01")) {
            renderLogo();
            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(buttonsBounceIn.getValue(), 0, 0);
            this.buttons.forEach(TitleScreenButton::render);
            GLOBAL_RENDER_STACK.popMatrix();
        }
        ThinGLUtils.end();
    }

    @Override
    public void hide() {
        AudioManager.getInstance().stop(id, 1000);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        this.buttons.forEach(b -> b.mouseClicked(mouseX, mouseY, button));
    }

    private DynamicAnimation titleBounceIn, buttonsBounceIn;
    private void renderLogo() {
        final Texture2D logo = Base.instance().assetsManager().texture("assets/base/uis/Blue_Archive.png");
        float logoWidth = 3385 / 5F, logoHeight = 1218 / 5F;
        float logoX = 1920 - logoWidth - 50, logoY = 19.98f;
        if (titleBounceIn == null) {
            titleBounceIn = AnimationUtils.build(700L, 1925, logoX, EasingFunction.EXPO);
            buttonsBounceIn = AnimationUtils.build(700L, logoWidth + 55, 0, EasingFunction.CIRC);
        }
        logoX = titleBounceIn.getValue();

        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, logo, logoX, logoY, logoWidth, logoHeight);
        TextUtils.textRun(31.95f, TextRun.fromString(FontUtils.SEMI_BOLD, "Scenario Engine"), logoX + logoWidth / 2, logoY + logoHeight);
    }

    private void updateSkeleton(Skeleton skeleton) {
        final WindowInterface window = ThinGL.windowInterface();

        skeleton.setPosition(0, 0);
        this.state.apply(skeleton);

        // Shitty way to scale and stuff? Well it has already been a problem since the legacy engine model of BASE so.....
        float scale = 0.00038F * ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2f);
        skeleton.setScale(scale, scale);
        skeleton.update(Gdx.graphics.getDeltaTime());
        skeleton.updateWorldTransform(Skeleton.Physics.none);
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }
}
