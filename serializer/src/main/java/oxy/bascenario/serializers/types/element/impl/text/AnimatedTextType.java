package oxy.bascenario.serializers.types.element.impl.text;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.render.elements.text.AnimatedText;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.element.ElementTypes;

import java.util.ArrayList;
import java.util.List;

public class AnimatedTextType implements TypeWithName<AnimatedText> {
    @Override
    public String type() {
        return "animated-text";
    }

    @Override
    public JsonElement write(AnimatedText text) {
        final JsonObject object = new JsonObject();
        final JsonArray array = new JsonArray();
        for (TextSegment segment : text.segments()) {
            array.add(ElementTypes.TEXT_SEGMENT_TYPE.write(segment));
        }
        object.add("segments", array);
        object.addProperty("size", text.size());
        object.addProperty("speed", text.playSpeed());
        return object;
    }

    @Override
    public AnimatedText read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<TextSegment> segments = new ArrayList<>();
        for (JsonElement element1 : object.getAsJsonArray("segments")) {
            segments.add(ElementTypes.TEXT_SEGMENT_TYPE.read(element1));
        }

        return new AnimatedText(object.get("speed").getAsFloat(), segments, object.get("size").getAsInt());
    }
}
