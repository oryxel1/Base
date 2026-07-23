package oxy.base.event.impl;

import oxy.base.api.event.ScreenEffectEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionScreenEffect extends FunctionEvent<ScreenEffectEvent> {
    public FunctionScreenEffect(ScreenEffectEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        if (event.type() == ScreenEffectEvent.Type.ADD) {
            screen.getEffects().add(event.effect());
        } else if (event.type() == ScreenEffectEvent.Type.CLEAR_ALL) {
            screen.getEffects().clear();
        }else {
            screen.getEffects().remove(event.effect());
        }
    }
}
