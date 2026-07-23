package oxy.base.screens.renderer.element.thingl.text;

import oxy.base.api.Scenario;
import oxy.base.api.event.dialogue.enums.OffsetType;
import oxy.base.api.event.dialogue.enums.TextOffset;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.api.render.elements.text.AnimatedText;
import oxy.base.api.render.elements.text.Text;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.screens.renderer.dialogue.DialogueRenderer;
import oxy.base.screens.renderer.element.base.ThinGLElementRenderer;

public class AnimatedTextRenderer extends ThinGLElementRenderer<AnimatedText> {
    private final DialogueRenderer renderer;

    public AnimatedTextRenderer(AnimatedText element, RenderLayer layer, Scenario scenario) {
        super(element, layer);

        this.renderer = new DialogueRenderer(scenario) {
            @Override
            public float renderYOffset() {
                return -((element.size() / 42f) * textYDistance() + 5);
            }

            @Override
            public void renderBackground() {
            }

            @Override
            public void renderDetails() {
            }
        };

        this.renderer.start(new TextOffset(OffsetType.Custom, position.x()), FontType.NotoSans, 0, "", "", false,
                Dialogue.builder().playSpeed(element.playSpeed()).dialogue(new Text(element.segments(), element.size())).build()
        );
    }

    @Override
    protected void renderThinGL() {
        this.renderer.renderDialogues();
    }
}
