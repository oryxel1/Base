package oxy.bascenario.api;

import oxy.bascenario.api.managers.AnimationManagerApi;
import oxy.bascenario.api.managers.AssetsManagerApi;
import oxy.bascenario.api.managers.ScenarioManagerApi;

public abstract class BaseApi {
    protected static BaseApi instance;
    public BaseApi() {
        instance = this;
    }

    protected ScenarioManagerApi scenarioManager;
    public ScenarioManagerApi scenarioManager() {
        return scenarioManager;
    }

    protected AnimationManagerApi animationManager;
    public AnimationManagerApi animationManager() {
        return animationManager;
    }

    protected AssetsManagerApi assetsManager;
    public AssetsManagerApi assetsManager() {
        return assetsManager;
    }

    public static BaseApi instance() {
        return instance;
    }

    public abstract void init();
    public abstract void shutdown();
}
