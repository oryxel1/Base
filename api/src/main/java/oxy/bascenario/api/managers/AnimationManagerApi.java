package oxy.bascenario.api.managers;

import oxy.bascenario.api.animation.api.Animation;

public interface AnimationManagerApi {
    Animation put(String name, Animation animation);
    Animation find(String name);
    void shutdown();
}
