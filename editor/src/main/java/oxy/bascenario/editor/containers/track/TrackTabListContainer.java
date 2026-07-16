package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.Base;
import oxy.bascenario.editor.containers.sequencer.VideoSequencerContainer;
import oxy.bascenario.utils.components.ButtonImage;

@Accessors(fluent = true)
public class TrackTabListContainer extends Container {
    @Getter
    private final Container container;

    public TrackTabListContainer(VideoSequencerContainer sequencerContainer) {
        super(BorderLayout.DEFAULT);

        this.addChild(new UpperContainer(sequencerContainer), c -> c.layoutOptions(BorderPosition.TOP));

        final ScrollContainer scrollContainer = new ScrollContainer(container = new Container(new VerticalListLayout()), false, true) {
            @Override
            public void render(Renderer renderer, Size size) {
                scrollY(sequencerContainer.trackListContainer().scrollY(), true);
                super.render(renderer, size);
            }
        };
        scrollContainer.barWidth().set(0f);

        this.addChild(scrollContainer, c -> c.layoutOptions(BorderPosition.CENTER));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withWidth(160);
    }

    private static class UpperContainer extends Container {
        public UpperContainer(VideoSequencerContainer sequencerContainer) {
            super(AbsoluteLayout.INSTANCE);

            final ButtonImage add = new ButtonImage(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_add.svg"), event -> {
                if (event.button() != MouseButton.LEFT) {
                    return;
                }

                sequencerContainer.createTrack();
            });
            add.hoverColor().set(Color.fromRGB(145, 218, 255));

            this.addChild(add, c -> c.layoutOptions(new AbsoluteOptions(5, 5)));
        }

        @Override
        public Size computeIdealSize(Size constraints) {
            return new Size(constraints.width(), 25);
        }
    }
}
