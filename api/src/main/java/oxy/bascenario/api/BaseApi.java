package oxy.bascenario.api;

public abstract class BaseApi {
    protected static BaseApi instance;
    public BaseApi() {
        init();
        instance = this;
    }

    public static BaseApi instance() {
        return instance;
    }

    public abstract void init();
}
