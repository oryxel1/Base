package oxy.bascenario.screens.renderer.element.thingl.emoticon;

import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.impl.*;

public class EmoticonMainRenderer extends ThinGLElementRenderer<Emoticon> {
    private final EmoticonRenderer renderer;

    public EmoticonMainRenderer(Emoticon element, RenderLayer layer) {
        super(element, layer);

        if (element.sound()) {
            AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/" + element.type().getSfx(), false, true), false), -1);
        }

        this.renderer = switch (element.type()) {
            case NOTE -> new EmoticonNoteRenderer(element.duration());
            case RESPOND -> new EmoticonRespondRenderer(element.duration());
            case ANGRY -> new EmoticonAngryRenderer(element.duration());
            case SWEAT -> new EmoticonSweatRenderer(element.duration());
            case THINKING -> new EmoticonDotBubbleRenderer(element.duration(), false);
            case HESITATED -> new EmoticonDotBubbleRenderer(element.duration(), true);
            case CHAT -> new EmoticonChatRenderer(element.duration());
            case EXCLAMATION_MARK -> new EmoticonExclamationMarkRenderer(element.duration());
            case HEART -> new EmoticonShyOrHeartRenderer(element.duration(), false);
            case SURPRISED -> new EmoticonSurprisedRenderer(element.duration());
            case QUESTION_MARK -> new EmoticonQuestionMarkRenderer(element.duration());
            case SHY -> new EmoticonShyOrHeartRenderer(element.duration(), true);
            case ANXIETY -> new EmoticonAnxietyRenderer(element.duration());
            case TWINKLE -> new EmoticonTwinkleRenderer(element.duration());
            case IDEA -> new EmoticonIdeaRenderer(element.duration());
        };

        this.renderer.init();
    }

    @Override
    protected void renderThinGL() {
        this.renderer.render();
    }

    @Override
    public boolean selfDestruct() {
        return renderer.finished();
    }
}
