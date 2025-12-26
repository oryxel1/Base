package oxy.bascenario;

import oxy.bascenario.api.BaseApi;
import oxy.bascenario.managers.AnimationManager;
import oxy.bascenario.managers.AssetsManager;
import oxy.bascenario.managers.ScenarioManager;

import java.io.File;

public final class Base extends BaseApi {
    public static final File SAVE_DIR = new File(new File(System.getProperty("user.home")), "Base");

    private Base() {
        super();
    }

    @Override
    public void init() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }

        animationManager = new AnimationManager();
        scenarioManager = new ScenarioManager();
        assetsManager = new AssetsManager();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void shutdown() {
        animationManager.shutdown();
        scenarioManager.shutdown();
    }

    @Override
    public AnimationManager animationManager() {
        return (AnimationManager) super.animationManager();
    }

    @Override
    public ScenarioManager scenarioManager() {
        return (ScenarioManager) super.scenarioManager();
    }

    @Override
    public AssetsManager assetsManager() {
        return (AssetsManager) super.assetsManager();
    }

    public static Base instance() {
        if (instance == null) {
            instance = new Base();
        }

        return (Base) instance;
    }
}
