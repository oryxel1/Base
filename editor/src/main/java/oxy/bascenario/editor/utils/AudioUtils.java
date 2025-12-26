package oxy.bascenario.editor.utils;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;

public class AudioUtils {
    public static long toDuration(File file) {
        if (file == null) {
            return 0;
        }
        try {
            AudioFile audioMetadata = AudioFileIO.read(file);
            return (long) (audioMetadata.getAudioHeader().getPreciseTrackLength() * 1000L);
        } catch (Exception ignored) {
        }
        return 0;
    }
}
