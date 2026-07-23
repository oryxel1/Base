package oxy.base.api.managers;

import oxy.base.api.animation.Animation;

public interface AnimationManagerApi {
    Animation put(String name, Animation animation);
    Animation find(String name);
    void shutdown();
}
