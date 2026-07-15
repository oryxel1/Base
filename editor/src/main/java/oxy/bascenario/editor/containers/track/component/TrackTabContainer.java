package oxy.bascenario.editor.containers.track.component;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.Base;
import oxy.bascenario.editor.containers.sequencer.VideoSequencerContainer;
import oxy.bascenario.utils.components.ButtonImage;

public class TrackTabContainer extends Container {
    private final TrackContainer container;

    public TrackTabContainer(VideoSequencerContainer sequencerContainer, TrackContainer container) {
        super(AbsoluteLayout.INSTANCE);
        this.container = container;

        final ButtonImage trash = new ButtonImage(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_trash.svg"), event -> {
            if (event.button() != MouseButton.LEFT) {
                return;
            }

            sequencerContainer.removeTrack(container, this);
        });
        trash.hoverColor().set(Color.fromRGB(145, 218, 255));
        this.addChild(trash, image -> image.layoutOptions(new AbsoluteLayoutOptions(5, 35)));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
//        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.RED);
        renderer.scale(0.4f, () -> renderer.text(this.rivet().backend().font().shapeText("Track " + container.index(), Color.fromRGB(148, 148, 148)), 15, 25, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), container.height());
    }
}
