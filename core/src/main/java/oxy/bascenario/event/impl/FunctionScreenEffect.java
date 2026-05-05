package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.ScreenEffectEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

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
