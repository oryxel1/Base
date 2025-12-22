package oxy.bascenario;

import lombok.Getter;
import oxy.bascenario.api.BaseApi;
import oxy.bascenario.managers.AnimationManager;
import oxy.bascenario.saving.ScenarioManager;

import java.io.File;

public final class Base extends BaseApi {
    public static final File SAVE_DIR = new File(new File(System.getProperty("user.home")), "Base");

    private Base() {
        super();
    }

    @Getter
    private ScenarioManager scenarioManager;
    @Getter
    private AnimationManager animationManager;

    @Override
    public void init() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }

        animationManager = new AnimationManager();
        scenarioManager = new ScenarioManager();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void shutdown() {
        animationManager.shutdown();
        scenarioManager.shutdown();
    }

    public static Base instance() {
        if (instance == null) {
            instance = new Base();
        }

        return (Base) instance;
    }
}
