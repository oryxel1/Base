package oxy.bascenario.screens.renderer.element.thingl.emoticon;

import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.impl.EmoticonNoteRenderer;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.impl.EmoticonRespondRenderer;

public class EmoticonMainRenderer extends ThinGLElementRenderer<Emoticon> {
    private final EmoticonRenderer renderer;

    public EmoticonMainRenderer(Emoticon element, RenderLayer layer) {
        super(element, layer);

        if (element.sound()) {
            AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/" + element.type().getSfx(), false, true)));
        }

        this.renderer = switch (element.type()) {
            case NOTE -> new EmoticonNoteRenderer(element.duration());
            case RESPOND -> new EmoticonRespondRenderer(element.duration());
            case ANGRY -> null;
            case SWEAT -> null;
            case THINKING -> null;
            case HESITATED -> null;
            case CHAT -> null;
            case EXCLAMATION_MARK -> null;
            case HEART -> null;
            case SURPRISED -> null;
            case QUESTION_MARK -> null;
            case SHY -> null;
            case ANXIETY -> null;
            case TWINKLE -> null;
        };
    }

    @Override
    protected void renderThinGL() {
        renderer.render();
    }
}
