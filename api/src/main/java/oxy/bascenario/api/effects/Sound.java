package oxy.bascenario.api.effects;

import lombok.Builder;
import oxy.bascenario.api.utils.FileInfo;

import java.util.concurrent.atomic.AtomicInteger;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Sound(int id, FileInfo file, float maxVolume, boolean loop) {
    private static final AtomicInteger SOUND_ID_COUNTER = new AtomicInteger(0);

    public static Sound sound(FileInfo info, boolean loop) {
        return new Sound(SOUND_ID_COUNTER.decrementAndGet(), info, 1, loop);
    }
}

