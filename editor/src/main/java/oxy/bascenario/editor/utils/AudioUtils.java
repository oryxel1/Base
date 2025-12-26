package oxy.bascenario.editor.utils;

import oxy.bascenario.Base;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.other.AudioAsset;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;

public class AudioUtils {
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
