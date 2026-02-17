package oxy.bascenario.api.event.dialogue.enums;

public record TextOffset(OffsetType type, float offset) {
    public static final TextOffset CENTER = new TextOffset(OffsetType.Center, 0);
    public static final TextOffset LEFT = new TextOffset(OffsetType.Left, 0);
    public static final TextOffset RIGHT = new TextOffset(OffsetType.Right, 0);
}
