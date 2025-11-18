package oxy.bascenario.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public abstract class Event<T>  {
    @Getter
    @Setter
    protected long duration;
    public abstract String type();

    public List<String> downloads() {
        return List.of();
    }

    public abstract T empty();
}