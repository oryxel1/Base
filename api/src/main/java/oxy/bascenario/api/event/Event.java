package oxy.bascenario.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public abstract class Event<T>  {
    @Getter
    @Setter
    protected long duration;
    public abstract String type();

    public abstract T empty();
}