package oxy.bascenario.screens.renderer.dialogue;

import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.TextSegment;
import oxy.bascenario.api.event.dialogue.enums.OffsetType;
import oxy.bascenario.api.event.dialogue.enums.TextOffset;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public abstract class BaseDialogueRenderer {
    protected static final float NON_GRADIENT_PART = 240, GRADIENT_PART = 120;
    protected static final float SEPARATOR_Y = 843.6F, SEPARATOR_WIDTH = 1555.2F - 6f, SEPARATOR_X = 182.4f + 3f;

    protected static final Color OUTLINE_COLOR = Color.fromRGB(41, 65, 90);

    protected String name, association;
    private boolean background;
    @Setter
    protected int currentIndex;

    protected boolean playing, finished;
    public boolean isBusy() {
        return this.playing && !this.finished;
    }

    public boolean hasClickedDialogue(double mouseX, double mouseY) {
        return mouseX >= 0 && mouseX <= 1920 && mouseY >= 720 && mouseY <= 1080;
    }

    private FontType font;
    protected TextOffset offset;
    public final void start(TextOffset offset, FontType type, int index, String name, String association, boolean background, Dialogue... dialogues) {
        if (index != currentIndex) {
            return;
        }

        this.offset = offset;

        this.font = type;
        this.name = name;
        this.association = association;

        this.stop();
        this.playing = true;
        this.background = background;
        add(index, true, dialogues);
    }

    public abstract void add(int dIndex, boolean newLine, Dialogue... dialogues);
    public abstract void renderDialogues();

    public void stop() {
        this.finished = false;
        this.playing = false;
    }

    public void render() {
        if (!this.playing) {
            return;
        }

        if (this.background) {
            renderBackground();
        }
        renderDetails();
        renderDialogues();
    }

    private void renderDetails() {
        final TextRun name = new TextRun(FontUtils.font(FontStyle.BOLD, this.font), new TextSegment(this.name, Color.WHITE, 0, OUTLINE_COLOR));
        TextUtils.textRun(58, name, SEPARATOR_X + 5, SEPARATOR_Y - 22, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.LOGICAL_LEFT);
        final float nameTextWidth = TextUtils.getVisualWidth(58, name.shape());

        final TextRun association = new TextRun(FontUtils.font(FontStyle.BOLD, this.font), new TextSegment(this.association, Color.fromRGB(132, 212, 249), 0, OUTLINE_COLOR));
        TextUtils.textRun(40, association, SEPARATOR_X + nameTextWidth + 25, SEPARATOR_Y - 23, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.LOGICAL_LEFT);
    }

    private void renderBackground() {
        final Color color = Color.fromRGBA(13, 31, 45, 220);
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 1080 - NON_GRADIENT_PART, 1920, 1080, color);

        for (int i = 1; i < GRADIENT_PART; i++) {
            int alpha = Math.max(0, Math.min(255, Math.round(220 + ((i / GRADIENT_PART) * (-220)))));
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 1080 - NON_GRADIENT_PART - i, 1920, 1080 - NON_GRADIENT_PART - i + 1, color.withAlpha(alpha));
        }

        // Render the separator.
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, SEPARATOR_X, SEPARATOR_Y, SEPARATOR_X + SEPARATOR_WIDTH, SEPARATOR_Y + 3, Color.fromRGB(110, 113, 115));
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, SEPARATOR_X, SEPARATOR_Y, SEPARATOR_X + SEPARATOR_WIDTH, SEPARATOR_Y + 1, Color.fromRGB(100, 103, 106));
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, SEPARATOR_X, SEPARATOR_Y + 2, SEPARATOR_X + SEPARATOR_WIDTH, SEPARATOR_Y + 3, Color.fromRGB(100, 103, 106));

    }
}
