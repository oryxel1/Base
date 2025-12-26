package oxy.bascenario.editor.utils;

import lombok.Builder;
import oxy.bascenario.api.effects.Sound;

// Just a duplicate of PlaySoundEvent (kinda) to be less confusing.
@Builder(toBuilder = true, builderClassName = "Builder")
public record SoundAsElement(Sound sound, int in, int out, float start, long max) {
}
