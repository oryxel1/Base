package oxy.bascenario.managers.other;

import javax.sound.sampled.AudioFormat;

public record AudioAsset(float[] samples, AudioFormat format, long duration) {
}
