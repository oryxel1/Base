package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Dialogue;
import com.bascenario.util.render.FontUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.TextSegment;
import org.joml.Matrix4fStack;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DialogueRender {
    @Getter
    private final Dialogue dialogue;
    @Getter
    private boolean finished, canSkip;

    private final List<StringBuilder> builders = new ArrayList<>();
    private int wordIndex, builderIndex;
    private long sinceLast = System.currentTimeMillis();

    public static boolean hasClickedDialogue(WindowInterface window, double mouseX, double mouseY) {
        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();
        float boxHeight = height * (1/3F);

        return mouseX >= 0 && mouseX <= width && mouseY >= boxHeight && mouseY <= height;
    }

    public void render(Matrix4fStack positionMatrix, WindowInterface window) {
        if (this.builders.isEmpty()) {
            this.builders.add(new StringBuilder());
        }

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();
        float boxHeight = window.getFramebufferHeight() * (1/3F);
        float noGradientPart = boxHeight * (2/3F);
        float theRestPart = boxHeight - noGradientPart;

        float separator = height - (boxHeight * (2/3F) * 0.985F);
        float separatorWidth = width - (0.19F * width);
        float separatorX = width / 2 - (separatorWidth / 2);

        // TODO: Actually support dialogue settings?
        int size42 = (int) Math.round(0.028 * ((width + height) / 2));

        long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / dialogue.playSpeed()) * (this.builders.getFirst().isEmpty() ? 10 : 1));
        if (System.currentTimeMillis() - this.sinceLast >= msPerWord) {
            if (this.wordIndex < dialogue.dialogue().length()) {
                char next = dialogue.dialogue().toCharArray()[this.wordIndex];
                String predictedAppend = this.builders.get(this.builderIndex).toString() + next;
                final TextRun testDialogue = TextRun.fromString(FontUtil.getFont("NotoSansRegular", size42), predictedAppend);
                if (ThinGL.rendererText().getExactWidth(testDialogue.shape()) >= separatorWidth - 5 || next == '\n') {
                    this.builderIndex++;
                    this.builders.add(new StringBuilder());
                }

                final StringBuilder builder = this.builders.get(this.builderIndex);
                if (!(builder.isEmpty() && String.valueOf(next).equals(" ")) && next != '\n') {
                    builder.append(next);
                }
            }
            this.wordIndex++;
            this.sinceLast = System.currentTimeMillis();
        }

        this.finished = this.wordIndex - dialogue.dialogue().length() >= 10;
        this.canSkip = this.wordIndex - dialogue.dialogue().length() >= -5;

        if (!this.dialogue.cutscene()) {
            int r = 36, g = 43, b = 57;
            ThinGL.renderer2D().filledRectangle(positionMatrix, 0, height - noGradientPart, width, height, Color.fromRGBA(r, g, b, 220));

            for (int i = 1; i < theRestPart; i++) {
                int alpha = Math.max(0, Math.min(255, Math.round(220 + ((i / theRestPart) * (-220)))));

                ThinGL.renderer2D().filledRectangle(positionMatrix, 0, height - noGradientPart - i,
                        width, height - noGradientPart - i + 1, Color.fromRGBA(r, g, b, alpha));
            }

            ThinGL.renderer2D().filledRectangle(positionMatrix, separatorX, separator, separatorX + separatorWidth, separator + 3, Color.fromRGB(116, 116, 126));
        }

        float y = 0;
        for (final StringBuilder builder : this.builders) {
            final TextRun dialogue = new TextRun(FontUtil.getFont("NotoSansRegular", size42), new TextSegment(builder.toString(),
                    Color.WHITE, 0, Color.fromRGB(50, 70, 90)));

            ThinGL.rendererText().textRun(positionMatrix, dialogue, separatorX + (0.00520833333F * width), separator + (0.07037037037F * height) + y, RendererText.VerticalOrigin.BOTTOM, RendererText.HorizontalOrigin.LEFT);
            y += ThinGL.rendererText().getExactHeight(dialogue.shape()) + 15;
        }

        int size58 = (int) Math.round(0.03866666666 * ((width + height) / 2));
        final TextRun name = new TextRun(FontUtil.getFont("NotoSansBold", size58), new TextSegment(this.dialogue.name(),
                Color.WHITE, 0, Color.fromRGB(50, 70, 90)));
        ThinGL.rendererText().textRun(positionMatrix, name, separatorX + (0.00260416666F * width), separator - (0.00555555555F * height),
                RendererText.VerticalOrigin.BOTTOM, RendererText.HorizontalOrigin.LEFT);

        int size40 = (int) Math.round(0.02666666666 * ((width + height) / 2));
        float nameTextSizeX = ThinGL.rendererText().getExactWidth(name.shape());
        final TextRun association = new TextRun(FontUtil.getFont("NotoSansBold", size40), new TextSegment(this.dialogue.association(),
                Color.fromRGB(132, 212, 249), 0, Color.fromRGB(50, 70, 90)));
        ThinGL.rendererText().textRun(positionMatrix, association, separatorX + nameTextSizeX + (0.015625F * width),
                separator - (0.01018518518F * height), RendererText.VerticalOrigin.BOTTOM, RendererText.HorizontalOrigin.LEFT);
    }
}
