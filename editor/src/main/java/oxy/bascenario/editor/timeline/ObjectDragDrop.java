package oxy.bascenario.editor.timeline;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ObjectDragDrop {
    private final ObjectOrEvent object;

    public abstract void reject();
    public abstract void accept();
}
