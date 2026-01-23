package oxy.bascenario.api.animation.api;

public enum AnimationMode {

    /**
     * The animation will play once and then stop.<br>
     * The last animation value will be returned.
     */
    DEFAULT,
    /**
     * Loop the animation from the beginning after it has finished.
     */
    LOOP,
    /**
     * Loop the animation in a ping-pong way (forward-backward-forward-backward...).
     */
    PING_PONG,

}