package oxy.bascenario.api.effects;

public record Fade(int duration) {
    public static Fade DISABLED = new Fade(-1);

    public Fade(int duration) {
        this.duration = duration;
        if (DISABLED != null && duration <= 0) {
            throw new RuntimeException("Fade duration need to be at least larger than 0! If fade is not present then use Fade.DISABLED.");
        }
    }
}
