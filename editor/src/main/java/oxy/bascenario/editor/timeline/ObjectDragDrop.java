package oxy.bascenario.editor.timeline;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public abstract class ObjectDragDrop {
    public final ObjectOrEvent object;

    @Getter
    private boolean waiting;
    public boolean loop;
    public void waitForResult() {
        this.waiting = true;
    }

    @Getter
    private boolean rejected;
    public void reject() {
        this.rejected = true;
    }

    public abstract void accept();
}
