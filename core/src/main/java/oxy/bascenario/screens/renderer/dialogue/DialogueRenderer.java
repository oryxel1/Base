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
import oxy.bascenario.utils.math.Pair;
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

    public void add(int index, boolean newLine, Dialogue... dialogues) {
        if (index != this.currentIndex) {
            return;
        }

        this.finished = false;
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

                    final Color outline = segment.outline().isPresent() ? segment.outline().get() : Color.TRANSPARENT;
                    final net.raphimc.thingl.text.TextSegment textSegment = new net.raphimc.thingl.text.TextSegment(String.valueOf(c), segment.color(), segment.toFlags(), outline);
                    builder.segments().add(new Pair<>(textSegment, font));
                }
            }

            int i = 0;

            final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / dialogue.getPlaySpeed()) * 1);

            long prev = 0;
            for (final TextBuilder builder : texts) {
                this.texts.add(new DialogueText(builder.segments(), dialogue.getPlaySpeed(), TimeUtils.currentTimeMillis(), prev, text.size(), i != 0 || newLine));
                i++;

                prev += msPerWord * builder.builder.length();
            }
        }
    }

    private record TextBuilder(StringBuilder builder, List<Pair<net.raphimc.thingl.text.TextSegment, Font>> segments) {
    }
    private record DialogueText(List<Pair<net.raphimc.thingl.text.TextSegment, Font>> allSegments, float speed,
                                long time, long prev, int size, boolean newLine) {
    }

    @Override
    public void stop() {
        super.stop();
        this.texts.clear();
    }

    @Override
    public void renderDialogues() {
        if (this.texts.isEmpty()) {
            return;
        }

        int done = 0;
        boolean finished = true;

        final List<TextLineCache> lines = new ArrayList<>();
        for (DialogueText text : this.texts) {
            if (!finished) {
                break;
            }

            final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / text.speed) * 1);
            long words = Math.min(Math.max(0, (TimeUtils.currentTimeMillis() - text.time() - text.prev()) / msPerWord), text.allSegments.size() - 1);

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

            if (lines.isEmpty() || text.newLine()) {
                lines.add(new TextLineCache(segments, text.size()));
            } else {
                lines.getLast().segments.addAll(segments);
            }
        }

        float y = SEPARATOR_Y + 2;
        for (TextLineCache line : lines) {
            y += (line.size / 42f) * 57 + 5;
            TextUtils.textLine(line.size(), new TextLine(line.segments()), SEPARATOR_X + 10, y, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.LOGICAL_LEFT);
        }

        this.finished = done == this.texts.size();
    }

    public record TextLineCache(List<TextRun> segments, float size) {
    }
}
