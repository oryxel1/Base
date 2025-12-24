package oxy.bascenario.api.render.elements.text;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Text(List<TextSegment> segments, int size) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        if (text.size != size || text.segments.size() != segments.size()) {
            return false;
        }

        int i = 0;
        for (TextSegment segment : text.segments()) {
            if (!segment.equals(segments.get(i))) {
                return false;
            }
            i++;
        }

        return true;
    }
}
