package oxy.bascenario.screens.renderer.dialogue;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.resource.font.Font;
import net.raphimc.thingl.text.TextLine;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.Pair;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.font.TextUtils;

import java.util.ArrayList;
import java.util.List;

public final class DialogueRenderer extends BaseDialogueRenderer {
    private final Scenario scenario;

    private final List<DialogueText> texts = new ArrayList<>();

    public DialogueRenderer(Scenario scenario) {
        this.scenario = scenario;
    }

    private long time, last;
    public void add(int index, Dialogue... dialogues) {
        if (index != this.currentIndex) {
            return;
        }

        if (time == 0) {
            time = TimeUtils.currentTimeMillis();
            last = TimeUtils.currentTimeMillis();
        }


        long time = 0;
        for (Dialogue dialogue : dialogues) {
            final List<TextBuilder> texts = new ArrayList<>();
            texts.add(new TextBuilder(new StringBuilder(), new ArrayList<>()));

            final Text text = dialogue.getDialogue();

            for (final TextSegment segment : text.segments()) {
                Font font = FontUtils.toFont(scenario, segment);

                for (char c : segment.text().toCharArray()) {
                    final String next = texts.get(texts.size() - 1).builder().toString() + c;
                    final TextRun run = new TextRun(font, new net.raphimc.thingl.text.TextSegment(next, Color.WHITE, segment.toFlags()));
                    if (TextUtils.getVisualWidth(text.size(), run.shape()) >= SEPARATOR_WIDTH - 5 || c == '\n') {
                        texts.add(new TextBuilder(new StringBuilder(), new ArrayList<>()));
                        if (c == '\n') {
                            continue;
                        }
                    }

                    final TextBuilder builder = texts.get(texts.size() - 1);
                    builder.builder().append(c);

                    final Color outline = segment.outline().isPresent() ? segment.outline().get() : OUTLINE_COLOR;
                    final net.raphimc.thingl.text.TextSegment textSegment = new net.raphimc.thingl.text.TextSegment(String.valueOf(c), segment.color(), segment.toFlags(), outline);
                    builder.segments().add(new Pair<>(textSegment, font));
                }
            }

            final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / dialogue.getPlaySpeed()) * 1);
            for (final TextBuilder builder : texts) {
                this.texts.add(new DialogueText(builder.segments(), dialogue.getPlaySpeed(), (TimeUtils.currentTimeMillis() - last) + time, text.size()));
                time += msPerWord * builder.builder.length();
            }
        }

        last = TimeUtils.currentTimeMillis();
        this.finished = false;
    }

    private record TextBuilder(StringBuilder builder, List<Pair<net.raphimc.thingl.text.TextSegment, Font>> segments) {
    }
    private record DialogueText(List<Pair<net.raphimc.thingl.text.TextSegment, Font>> allSegments, float speed,
                                long distance, int size) {
    }

    @Override
    public void stop() {
        super.stop();
        this.texts.clear();
        last = time = 0;
    }

    @Override
    public void renderDialogues() {
        if (this.texts.isEmpty()) {
            return;
        }

        float y = SEPARATOR_Y + 64;

        int done = 0;
        boolean finished = true;
        for (DialogueText text : this.texts) {
            if (!finished) {
                break;
            }

            final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / text.speed) * 1);
            long words = Math.min((TimeUtils.currentTimeMillis() - time - text.distance) / msPerWord, text.allSegments.size() - 1);

            finished = words == text.allSegments.size() - 1;
            if (finished) {
                done++;
            }

            final List<TextRun> segments = new ArrayList<>();
            int length = -1;
            for (Pair<net.raphimc.thingl.text.TextSegment, Font> pair : text.allSegments) {
                if (length == words) {
                    break;
                }
                length++;

                final TextRun newRun = new TextRun(pair.right(), pair.left());
                if (segments.isEmpty()) {
                    segments.add(newRun);
                } else {
                    final TextRun last = segments.get(segments.size() - 1);
                    // Probably not the best idea to do this... But who cares!
                    boolean same = pair.right().getFamilyName().equals(last.font().getFamilyName()) && pair.right().getSubFamilyName().equals(last.font().getSubFamilyName());
                    if (same) {
                        last.add(pair.left());
                    } else {
                        segments.add(newRun);
                    }
                }
            }

            final TextLine line = new TextLine(segments);
            TextUtils.textLine(text.size(), line, SEPARATOR_X + 10, y, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.LOGICAL_LEFT);
            y += TextUtils.getLogicalHeight(text.size(), line.shape()) + 5;
        }

        this.finished = done == this.texts.size();
    }
}
