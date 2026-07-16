package oxy.bascenario.editor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class EditorValues {
    @Getter
    @Setter
    private static EditorValues instance;

    private TimelineType type = TimelineType.Sequencer;

    private boolean playing;
    private long timestamp = 1000L;
    public void timestamp(long timestamp) {
        this.timestamp = timestamp;
        this.playing = false;
    }

    private float scale = 1;
    private float scroll;

    private static final float ONE_SECOND_WIDTH = 128;

    public float oneSecondWidth() {
        return ONE_SECOND_WIDTH * scale;
    }
    public float oneMilSecondWidth() {
        return (ONE_SECOND_WIDTH / 1000f) * scale;
    }

    private long since = 0;
    public void tick() {
        if (since == 0) {
            since = System.currentTimeMillis();
        }
        if (playing) {
            timestamp += System.currentTimeMillis() - since;
        }
        since = System.currentTimeMillis();
    }

    public enum TimelineType {
        Dope_Sheet, Sequencer
    }
}
