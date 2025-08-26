package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.util.render.FontUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.TextSegment;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class DialogueRender {
    private final Scenario.Dialogue dialogue;
    @Getter
    private boolean finished;

    private final StringBuilder builder = new StringBuilder();
    private int wordIndex;
    private long sinceLast = System.currentTimeMillis();

    public static boolean hasClickedDialogue(WindowInterface window, double mouseX, double mouseY) {
        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();
        float boxHeight = height * (1/3F);

        return mouseX >= 0 && mouseX <= width && mouseY >= boxHeight && mouseY <= height;
    }

    public void render(Matrix4fStack positionMatrix, WindowInterface window) {
        long msPerWord = (long) (Scenario.Dialogue.MS_PER_WORD * (1 / dialogue.playSpeed()) * (builder.isEmpty() ? 10 : 1));
        if (System.currentTimeMillis() - this.sinceLast >= msPerWord) {
            if (this.wordIndex < dialogue.dialogue().length()) {
                this.builder.append(dialogue.dialogue().toCharArray()[this.wordIndex]);
            }
            this.wordIndex++;
            this.sinceLast = System.currentTimeMillis();
        }

        this.finished = this.wordIndex - dialogue.dialogue().length() >= 10;

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();
        float boxHeight = window.getFramebufferHeight() * (1/3F);
        float noGradientPart = boxHeight * (2/3F);
        float theRestPart = boxHeight - noGradientPart;

        int r = 36, g = 43, b = 57;
        ThinGL.renderer2D().filledRectangle(positionMatrix, 0, height - noGradientPart, width, height, Color.fromRGBA(r, g, b, 220));

        for (int i = 1; i < theRestPart; i++) {
            int alpha = Math.max(0, Math.min(255, Math.round(220 + ((i / theRestPart) * (-220)))));

            ThinGL.renderer2D().filledRectangle(positionMatrix, 0, height - noGradientPart - i,
                    width, height - noGradientPart - i + 1, Color.fromRGBA(r, g, b, alpha));
        }

        float separator = height - (boxHeight * (2/3F) * 0.985F);
        float separatorWidth = width - (0.19F * width);
        float separatorX = width / 2 - (separatorWidth / 2);
        ThinGL.renderer2D().filledRectangle(positionMatrix, separatorX, separator, separatorX + separatorWidth, separator + 3, Color.fromRGB(116, 116, 126));

        // TODO: Actually support dialogue settings?
        int size42 = (int) Math.round(0.028 * ((width + height) / 2));
        final TextRun dialogue = TextRun.fromString(FontUtil.getFont("NotoSansRegular", size42), this.builder.toString());
        ThinGL.rendererText().textRun(positionMatrix, dialogue, separatorX + (0.00520833333F * width), separator + (0.02777777777F * height));

        int size58 = (int) Math.round(0.03866666666 * ((width + height) / 2));
        final TextRun name = new TextRun(FontUtil.getFont("NotoSansBold", size58), new TextSegment(this.dialogue.name(),
                Color.WHITE, 0, Color.fromRGB(50, 70, 90)));
        ThinGL.rendererText().textRun(positionMatrix, name, separatorX + (0.00494791666F * width), separator - (0.06296296296F * height));

        int size40 = (int) Math.round(0.02666666666 * ((width + height) / 2));
        float nameTextSizeX = ThinGL.rendererText().getExactWidth(name.shape());
        final TextRun association = new TextRun(FontUtil.getFont("NotoSansBold", size40), new TextSegment(this.dialogue.association(),
                Color.fromRGB(132, 212, 249), 0, Color.fromRGB(50, 70, 90)));
        ThinGL.rendererText().textRun(positionMatrix, association, separatorX + nameTextSizeX + (0.01614583333F * width), separator - (0.05F * height));

        // 1280 889
//        ImmediateMultiDrawBatchDataHolder drawBatch = new ImmediateMultiDrawBatchDataHolder();
//        ThinGL.renderer2D().beginBuffering(drawBatch);

//        ThinGL.renderer2D().filledRectangle(positionMatrix, 0, height, width, height - noGradientPart, Color.fromRGBA(192, 192, 192, 180));
//        ThinGL.renderer2D().endBuffering();

//        drawBatch.draw();
//        drawBatch.free();
    }
}
