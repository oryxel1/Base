package oxy.bascenario.api.event.api;

public abstract class Event<T>  {
    public abstract String type();
    public abstract T empty();
}