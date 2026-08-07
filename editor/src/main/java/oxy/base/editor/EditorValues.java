package oxy.base.editor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.math.MathUtils;
import oxy.base.editor.containers.dopesheet.DopeSheetTrack;
import oxy.base.editor.object.ObjectOrEvent;
import oxy.base.editor.object.values.ObjectTransform;

import java.util.HashMap;
import java.util.Map;

// Bull shit stuff that got put in 1 class so it can be easily access.
@Accessors(fluent = true)
@Getter
@Setter
public class EditorValues {
    @Getter
    @Setter
    private static EditorValues instance;

    private ObjectOrEvent selectedObject;
    private TimelineType type = TimelineType.Sequencer;

    private final Map<ObjectTransform, DopeSheetTrack> dopeSheetTrackMap = new HashMap<>();

    private boolean playing;
    private long timestamp = 1000L;
    public void timestamp(long timestamp) {
        this.timestamp = timestamp;
        this.playing = false;
    }

    private float scale = 1;
    public void scale(float scale) {
        this.scale = MathUtils.clamp(scale, 0.5f, 2f);
    }

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
