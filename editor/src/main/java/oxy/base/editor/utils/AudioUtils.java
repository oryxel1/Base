package oxy.base.editor.utils;

import oxy.base.Base;
import oxy.base.api.managers.other.Asset;
import oxy.base.api.utils.FileInfo;
import oxy.base.managers.other.AudioAsset;

public class AudioUtils {
    public static long toDuration(String scenario, SoundAsElement sound) {
        long duration = toDuration(scenario, sound.sound().file());
        duration -= (long) (sound.start() * 1000L);
        return duration;
    }

    public static long toDuration(String scenario, FileInfo info) {
        try {
            final Asset<?> asset = Base.instance().assetsManager().assets(scenario, info);
            if (asset.asset() instanceof AudioAsset audioAsset) {
                return audioAsset.duration();
            }
        } catch (Exception ignored) {
        }

        return 0;
    }
}
