package oxy.bascenario.managers;

import oxy.bascenario.Base;
import oxy.bascenario.api.animation.Animation;
import oxy.bascenario.api.animation.AnimationTimeline;
import oxy.bascenario.api.animation.AnimationValue;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.managers.AnimationManagerApi;
import oxy.bascenario.utils.math.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimationManager extends HashMap<String, Animation> implements AnimationManagerApi {
    private static final File SAVE_DIR = new File(Base.SAVE_DIR, "animations");

    public AnimationManager() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }

        initDefaultAnimations();
    }

    public Pair<Integer, String[]> getAllAnimations(String current) {
        final List<String> animations = new ArrayList<>();

        boolean has = this.containsKey(current);
        if (!has) {
            animations.add(current + " (Not available)");
        }

        int index = 0;
        for (String key : this.keySet()) {
            if (has && key.equals(current)) {
                index = animations.size();
            }
            animations.add(key);
        }

        return new Pair<>(index, animations.toArray(new String[0]));
    }

    @Override
    public Animation find(String name) {
        return get(name);
    }

    public void shutdown() {
    }

    private void initDefaultAnimations() {
        this.put(
                "bascenarioengine:default-shake",
                Animation.builder()
                        .name("Default Shake")
                        .offset(new AnimationValue(new String[] {"math.abs(query.offset(0) + 19.2) <= 0.0001 ? 19.2 : -19.2", "0"}, "0.08", Easing.LINEAR))
                        .defaultOffset(new AnimationValue(new String[]{"0", "0"}, "0.08", Easing.LINEAR))
                        .maxDuration(0.26f)
                        .resetWhenFinish(true)
                        .build()
        );

        this.put(
                "bascenarioengine:down-then-up",
                Animation.builder()
                        .name("Down Then Up")
                        .put(0, AnimationTimeline.builder().offset(new AnimationValue(new String[] {"0", "108"}, "0.3", Easing.LINEAR)).build())
                        .defaultOffset(new AnimationValue(new String[]{"0", "0"}, "0.3", Easing.LINEAR))
                        .maxDuration(0.3f)
                        .resetWhenFinish(true)
                        .build()
        );

        this.put(
                "bascenarioengine:hangry",
                Animation.builder()
                        .name("Default Angry")
                        .put(0, AnimationTimeline.builder().offset(new AnimationValue(new String[] {"0", "-108"}, "0.2", Easing.LINEAR)).build())
                        .put(0.2f, AnimationTimeline.builder().offset(new AnimationValue(new String[] {"0", "0"}, "0.2", Easing.LINEAR)).build())
                        .put(0.4f, AnimationTimeline.builder().offset(new AnimationValue(new String[] {"0", "-108"}, "0.2", Easing.LINEAR)).build())
                        .defaultOffset(new AnimationValue(new String[]{"0", "0"}, "0.3", Easing.LINEAR))
                        .maxDuration(0.6f)
                        .resetWhenFinish(true)
                        .build()
        );

        this.put(
                "bascenarioengine:test",
                Animation.builder()
                        .name("Dev Test")
                        .put(0, AnimationTimeline.builder().offset(new AnimationValue(new String[] {"-960", "0"}, "1", Easing.LINEAR)).build())
                        .put(1f, AnimationTimeline.builder().scale(new AnimationValue(new String[] {"1.5", "1.5"}, "0.3", Easing.QUAD)).build())
                        .put(1.3f, AnimationTimeline.builder().scale(new AnimationValue(new String[] {"1", "1"}, "0.3", Easing.QUAD)).build())
                        .defaultOffset(new AnimationValue(new String[]{"0", "0"}, "0.1", Easing.LINEAR))
                        .maxDuration(1.6f)
                        .resetWhenFinish(true)
                        .build()
        );

        this.put(
                "bascenarioengine:loop-test",
                Animation.builder()
                        .name("Dev Loop Test")
                        .rotation(new AnimationValue(new String[] {"0", "0", "57.29577951308232 * (math.mod(q.currentTimeMillis, 500) / 500 * math.pi * 2)"}, "0", Easing.LINEAR)).build()
        );
    }
}
