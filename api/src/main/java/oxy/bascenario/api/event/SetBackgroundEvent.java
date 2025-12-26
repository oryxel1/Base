package oxy.bascenario.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.FileInfo;

@RequiredArgsConstructor
@Getter
public class SetBackgroundEvent extends Event<SetBackgroundEvent> {
    private final FileInfo background;

    @Override
    public String type() {
        return "set-background";
    }

    @Override
    public SetBackgroundEvent empty() {
        return new SetBackgroundEvent(null);
    }
}
