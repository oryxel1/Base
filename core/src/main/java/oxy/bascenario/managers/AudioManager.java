package oxy.bascenario.managers;

import lombok.*;
import net.raphimc.audiomixer.SourceDataLineAudioMixer;
import net.raphimc.audiomixer.pcmsource.impl.StereoStaticPcmSource;
import net.raphimc.audiomixer.sound.impl.pcm.StereoSound;
import net.raphimc.audiomixer.soundmodifier.impl.VolumeModifier;
import oxy.bascenario.Base;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.managers.other.AudioAsset;
import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.utils.animation.AnimationUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AudioManager {
    private final SourceDataLineAudioMixer mixer;

    @Getter
    private static final AudioManager instance = new AudioManager();
    private AudioManager() {
        if (instance != null) {
            throw new RuntimeException("This class can only create one instance!");
        }

        AudioFormat format = new AudioFormat(48000, 16, 2, true, false);
        try {
            this.mixer = new SourceDataLineAudioMixer(AudioSystem.getSourceDataLine(format));
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<Integer, CachedSound> cachedSounds = new HashMap<>();

    public void play(Sound sound, long fadeIn) {
        play(null, sound, 0, fadeIn);
    }

    public void play(String scenario, Sound sound, long fadeIn) {
        play(scenario, sound, 0, fadeIn);
    }

    @SneakyThrows
    public void play(String scenario, Sound sound, float start, long fadeIn) {
        final Asset<AudioAsset> asset = Base.instance().assetsManager().assets(scenario, sound.file());
        boolean fade = fadeIn > 0;

        final StereoStaticPcmSource source = new StereoStaticPcmSource(asset.asset().samples());
        StereoSound stereoSound = new StereoSound(source);
        VolumeModifier modifier = new VolumeModifier(fade ? 0 : sound.maxVolume());
        stereoSound.getSoundModifiers().append(modifier);

        final CachedSound cache = new CachedSound(stereoSound, modifier, sound);
        if (fade) {
            cache.fadeIn = AnimationUtils.build(fadeIn, 0, sound.maxVolume(), EasingFunction.LINEAR);
        }
        source.setPosition(start);
        cache.loop = sound.loop();

        CachedSound old = this.cachedSounds.get(sound.id());
        if (old != null) {
            old.stop();
        }
        cache.resume();

        this.cachedSounds.put(sound.id(), cache);
    }

    public void fade(int id, long duration, float volume, Easing easing) {
        if (duration <= 0) {
            return;
        }

        final CachedSound cache = this.cachedSounds.get(id);
        if (cache != null) {
            cache.fade = AnimationUtils.build(duration, 0, Math.abs(volume), AnimationUtils.toFunction(easing));
        }
    }

    public void pause(int id) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache != null) {
            cache.stop();
        }
    }

    public void resume(int id) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache != null) {
            cache.resume();
        }
    }

    public void stop(int id, int fadeDuration) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache == null) {
            return;
        }
        boolean fade = fadeDuration > 0;
        if (fade) {
            cache.fadeOut = AnimationUtils.build(fadeDuration, cache.getVolume(), 0, EasingFunction.LINEAR);
        } else {
            cache.stop();
            this.cachedSounds.remove(id);
        }
    }

    public void tick() {
        final Iterator<Map.Entry<Integer, CachedSound>> iterator = this.cachedSounds.entrySet().iterator();
        while (iterator.hasNext()) {
            final CachedSound cache = iterator.next().getValue();
            if (cache == null) {
                continue;
            }

            if (cache.stereoSound.isFinished()) {
                if (cache.loop) {
                    ((StereoStaticPcmSource)cache.stereoSound.getPcmSource()).setPosition(0);
                    cache.resume();
                } else if (cache.fadeOut == null) {
                    cache.stop();
                    iterator.remove();
                    continue;
                }
            }

            if (cache.fadeOut != null) {
                if (cache.fadeOut.isRunning()) {
                    cache.setVolume(cache.fadeOut.getValue());
                } else {
                    cache.stop();
                    iterator.remove();
                }
            } else if (cache.fadeIn != null) {
                if (cache.fadeIn.isRunning()) {
                    cache.setVolume(cache.fadeIn.getValue());
                } else {
                    cache.setVolume(cache.fadeIn.getTarget());
                    cache.fadeIn = null;
                }
            } else if (cache.fade != null) {
                if (cache.fade.isRunning()) {
                    cache.setVolume(cache.fade.getValue());
                } else {
                    cache.setVolume(cache.fade.getTarget());
                    cache.fade = null;
                }
            }
        }
    }

    @RequiredArgsConstructor
    private static class CachedSound {
        private final StereoSound stereoSound;
        private final VolumeModifier modifier;
        private final Sound sound;
        private DynamicAnimation fadeOut, fadeIn;
        private DynamicAnimation fade;
        private boolean loop;

        private float getVolume() {
            return modifier.getVolume();
        }

        private void setVolume(float value) {
            modifier.setVolume(value);
        }

        private void resume() {
            AudioManager.getInstance().mixer.stopSound(stereoSound); // we have to ensure there isn't a duplicate.
            AudioManager.getInstance().mixer.playSound(stereoSound);
        }

        private void stop() {
            AudioManager.getInstance().mixer.stopSound(stereoSound);
        }
    }
}