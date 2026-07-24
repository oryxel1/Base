package oxy.base.editor.object.values;

public enum ObjectTransform {
    POSITION_X, POSITION_Y,
    SCALE_X, SCALE_Y,
    PIVOT_X, PIVOT_Y,
    ROTATE_X, ROTATE_Y, ROTATE_Z;

    public float defaultValue() {
        return this == SCALE_X || this == SCALE_Y ? 1 : 0;
    }
}
