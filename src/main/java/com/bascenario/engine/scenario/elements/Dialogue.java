package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
public final class Dialogue {
    public static long MS_PER_WORD = 38L;

    @SerializedName("dialogue-index")
    private int index;
    private String dialogue;
    private String name;
    private String association;
    @SerializedName("play-speed")
    private float playSpeed;
    @SerializedName("text-scale")
    private float textScale;
    @SerializedName("font-type")
    private FontType fontType;
    private boolean cutscene;
    @SerializedName("close-dialogue-on-click")
    private boolean closeOnClick;

    public Dialogue(int index, String dialogue, String name, String association,
                    float playSpeed,
                    float textScale,
                    FontType fontType, boolean cutscene,
                    boolean closeOnClick) {
        this.index = index;
        this.dialogue = dialogue;
        this.name = name;
        this.association = association;
        this.playSpeed = playSpeed;
        this.textScale = textScale;
        this.fontType = fontType;
        this.cutscene = cutscene;
        this.closeOnClick = closeOnClick;
    }

    public int index() {
        return index;
    }

    public void index(int index) {
        this.index = index;
    }

    public String dialogue() {
        return dialogue;
    }

    public void dialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    public String association() {
        return association;
    }

    public void association(String association) {
        this.association = association;
    }

    public float playSpeed() {
        return playSpeed;
    }

    public void playSpeed(float playSpeed) {
        this.playSpeed = playSpeed;
    }

    public float textScale() {
        return textScale;
    }

    public void textScale(float textScale) {
        this.textScale = textScale;
    }

    public FontType fontType() {
        return fontType;
    }

    public void fontType(FontType fontType) {
        this.fontType = fontType;
    }

    public boolean cutscene() {
        return cutscene;
    }

    public void cutscene(boolean cutscene) {
        this.cutscene = cutscene;
    }

    public boolean closeOnClick() {
        return closeOnClick;
    }

    public void closeOnClick(boolean closeOnClick) {
        this.closeOnClick = closeOnClick;
    }

    public enum FontType {
        REGULAR, SEMI_BOLD, BOLD;

        public static String getFontName(FontType type) {
            if (type == null) {
                return "NotoSansRegular";
            }

            return switch (type) {
                case SEMI_BOLD -> "NotoSansSemiBold";
                case BOLD -> "NotoSansBold";
                default -> "NotoSansRegular";
            };
        }
    }
}