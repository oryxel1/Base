package oxy.bascenario.screens.renderer;

import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextLine;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.TextSegment;
import net.raphimc.thingl.text.font.Font;
import oxy.bascenario.api.elements.Dialogue;
import oxy.bascenario.api.elements.FontType;
import oxy.bascenario.utils.FontUtils;

import java.util.ArrayList;
import java.util.List;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public final class DialogueRenderer {
    private static Font NAME, ASSOCIATION;
    public void create() {
        NAME = FontUtils.getFont("NotoSansBold", 58);
        ASSOCIATION = FontUtils.getFont("NotoSansBold", 40);
    }

    private static final float NON_GRADIENT_PART = 240, GRADIENT_PART = 120;
    private static final float SEPARATOR_Y = 843.6F, SEPARATOR_WIDTH = 1555.2F, SEPARATOR_X = 182.4f;

    private static final Color OUTLINE_COLOR = Color.fromRGB(50, 70, 90);

    private String name, association;
    private boolean background;
    @Setter
    private int currentIndex;

    private boolean playing, finished;
    public boolean isBusy() {
        return this.playing && !this.finished;
    }

    public boolean hasClickedDialogue(double mouseX, double mouseY) {
        return mouseX >= 0 && mouseX <= 1920 && mouseY >= 720 && mouseY <= 1080;
    }

    public void start(int index, String name, String association, boolean background, Dialogue... dialogues) {
        this.name = name;
        this.association = association;

        this.stop();
        this.playing = true;
        this.background = background;
        for (Dialogue dialogue : dialogues) {
            add(index, dialogue);
        }
    }

    public void add(int dIndex, Dialogue dialogue) {
        if (dIndex != currentIndex) {
            return;
        }

        Font regular = FontUtils.getFont(FontType.toName(FontType.REGULAR), 42);
        Font bold = FontUtils.getFont(FontType.toName(FontType.BOLD), 42);
        Font semiBold = FontUtils.getFont(FontType.toName(FontType.SEMI_BOLD), 42);

        List<TextBuilder> builders = new ArrayList<>();
        int index = 0;
        for (oxy.bascenario.api.elements.TextSegment segment : dialogue.getDialogue()) {
            if (!builders.isEmpty()) {
                index++;
            }
            builders.add(new TextBuilder(new StringBuilder(), !this.texts.isEmpty(), segment));

            for (char c : segment.text().toCharArray()) {
                String predictedAppend = builders.get(index).builder.toString() + c;
                final TextRun run = new TextRun(switch (segment.type()) {
                    case REGULAR -> regular;
                    case SEMI_BOLD -> bold;
                    case BOLD -> semiBold;
                }, new TextSegment(predictedAppend, Color.WHITE, 0, OUTLINE_COLOR));
                if (ThinGL.rendererText().getVisualWidth(run.shape()) >= SEPARATOR_WIDTH - 5 || c == '\n') {
                    builders.add(new TextBuilder(new StringBuilder(), true, segment));
                    index++;

                    if (c == '\n') {
                        continue;
                    }
                }

                builders.get(index).builder.append(c);
            }
        }

        for (TextBuilder builder : builders) {
            this.texts.add(new TextCache(dialogue, builder.segment, switch (builder.segment.type()) {
                case REGULAR -> regular;
                case SEMI_BOLD -> bold;
                case BOLD -> semiBold;
            }, builder.builder.toString(), builder.down));
        }

        this.finished = false;
    }

    private record TextBuilder(StringBuilder builder, boolean down, oxy.bascenario.api.elements.TextSegment segment) {}

    public void stop() {
        this.finished = false;
        this.playing = false;
        this.texts.clear();
    }

    public void render() {
        if (!this.playing) {
            return;
        }

        if (this.background) {
            renderBackground();
        }
        renderDetails();

        if (this.sinceWord == 0) {
            this.sinceWord = System.currentTimeMillis();
        }
        renderDialogues();
    }

    private long sinceWord;
    private final List<TextCache> texts = new ArrayList<>();
    private void renderDialogues() {
        if (this.texts.isEmpty()) {
            return;
        }


        float y = SEPARATOR_Y + 76;
        float lastHeight = 0;
        int count = 0;

        TextLine line = null;
        for (TextCache cache : this.texts) {
            if (cache.down) {
                if (line != null) {
                    ThinGL.rendererText().textLine(GLOBAL_RENDER_STACK, line, SEPARATOR_X + 10, y, RendererText.VerticalOrigin.LOGICAL_BOTTOM, RendererText.HorizontalOrigin.LOGICAL_LEFT);
                }

                line = null;
                y += lastHeight;
            }

            final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / cache.dialogue.getPlaySpeed()) * 1);

            boolean finished = cache.count == cache.text.length();
            if (finished) {
                count++;
            }
            if (System.currentTimeMillis() - this.sinceWord >= msPerWord && !finished) {
                this.sinceWord = System.currentTimeMillis();
                cache.count++;
            }

            final TextRun textRun = new TextRun(cache.font, new TextSegment(cache.text.substring(0, cache.count), cache.segment.color(), 0, OUTLINE_COLOR));
            if (!cache.down) {
                if (line == null) {
                    line = new TextLine(new ArrayList<>());
                }
                line.add(textRun);
            } else {
                ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, textRun, SEPARATOR_X + 10, y, RendererText.VerticalOrigin.LOGICAL_BOTTOM, RendererText.HorizontalOrigin.LOGICAL_LEFT);
            }

            lastHeight = ThinGL.rendererText().getLogicalHeight(textRun.shape()) + 5;
        }

        if (line != null) {
            ThinGL.rendererText().textLine(GLOBAL_RENDER_STACK, line, SEPARATOR_X + 10, y, RendererText.VerticalOrigin.LOGICAL_BOTTOM, RendererText.HorizontalOrigin.LOGICAL_LEFT);
        }

        this.finished = count == this.texts.size();
    }

    private void renderDetails() {
        final TextRun name = new TextRun(NAME, new TextSegment(this.name, Color.WHITE, 0, OUTLINE_COLOR));
        ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, name, SEPARATOR_X + 5, SEPARATOR_Y - 6, RendererText.VerticalOrigin.LOGICAL_BOTTOM, RendererText.HorizontalOrigin.LOGICAL_LEFT);
        final float nameTextWidth = ThinGL.rendererText().getVisualWidth(name.shape());

        final TextRun association = new TextRun(ASSOCIATION, new TextSegment(this.association, Color.fromRGB(132, 212, 249), 0, OUTLINE_COLOR));
        ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, association, SEPARATOR_X + nameTextWidth + 30, SEPARATOR_Y - 11, RendererText.VerticalOrigin.LOGICAL_BOTTOM, RendererText.HorizontalOrigin.LOGICAL_LEFT);
    }

    private void renderBackground() {
        final Color color = Color.fromRGBA(13, 31, 45, 220);
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 1080 - NON_GRADIENT_PART, 1920, 1080, color);

        for (int i = 1; i < GRADIENT_PART; i++) {
            int alpha = Math.max(0, Math.min(255, Math.round(220 + ((i / GRADIENT_PART) * (-220)))));
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 1080 - NON_GRADIENT_PART - i, 1920, 1080 - NON_GRADIENT_PART - i + 1, color.withAlpha(alpha));
        }

        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, SEPARATOR_X, SEPARATOR_Y, SEPARATOR_X + SEPARATOR_WIDTH, SEPARATOR_Y + 3, Color.fromRGB(116, 116, 126));
    }


    private static final class TextCache {
        private final Dialogue dialogue;
        private final oxy.bascenario.api.elements.TextSegment segment;
        private final Font font;
        private final String text;
        private final boolean down;
        private int count = 0;

        private TextCache(Dialogue dialogue, oxy.bascenario.api.elements.TextSegment segment, Font font, String text, boolean down) {
            this.dialogue = dialogue;
            this.segment = segment;
            this.text = text;
            this.font = font;
            this.down = down;
        }
    }
}
