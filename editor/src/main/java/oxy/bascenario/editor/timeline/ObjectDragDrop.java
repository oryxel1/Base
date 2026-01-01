package oxy.bascenario.editor.timeline;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public abstract class ObjectDragDrop {
    public final ObjectOrEvent object;

    public long nearestTime = Long.MAX_VALUE;
    public void time(long time, long last) {
        if (time < last && Math.abs(time - last) < Math.abs(time - nearestTime)) {
            nearestTime = last;
        }
    }

    @Getter
    private boolean waiting;
    public boolean loop, second;
    public void waitForResult() {
        this.waiting = true;
    }

    @Getter
    private boolean rejected;
    public void reject() {
        this.rejected = true;
    }
    public void reset() {
        this.rejected = false;
    }

    public abstract void accept();
}
