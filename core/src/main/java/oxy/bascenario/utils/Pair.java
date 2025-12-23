package oxy.bascenario.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class Pair<A, B> {
    private A left;
    private B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A left() {
        return left;
    }

    public B right() {
        return right;
    }

    public void left(A left) {
        this.left = left;
    }

    public void right(B right) {
        this.right = right;
    }
}
