package oxy.bascenario.editor.screens.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.Pair;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@ToString
public class Track {
    private final Timeline timeline;
    private final int index;

    // key: start time, pair: a=element, b=end time.
    private final Map<Long, Pair<Cache, Long>> elements = new HashMap<>();

    public void render(float x, float y) {

    }

    public record Cache(Object object, RenderLayer layer, Integer attachedTo) {
    }
}
