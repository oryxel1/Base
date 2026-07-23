package oxy.base.serializers.types.element.impl.text;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.render.elements.text.Text;
import oxy.base.api.render.elements.text.TextSegment;
import oxy.base.serializers.base.TypeWithName;
import oxy.base.serializers.types.element.ElementTypes;

import java.util.ArrayList;
import java.util.List;

public class TextType implements TypeWithName<Text> {
    @Override
    public String type() {
        return "text";
    }

    @Override
    public JsonElement write(Text text) {
        final JsonObject object = new JsonObject();
        final JsonArray array = new JsonArray();
        for (TextSegment segment : text.segments()) {
            array.add(ElementTypes.TEXT_SEGMENT_TYPE.write(segment));
        }
        object.add("segments", array);
        object.addProperty("size", text.size());
        return object;
    }

    @Override
    public Text read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final List<TextSegment> segments = new ArrayList<>();
        for (JsonElement element1 : object.getAsJsonArray("segments")) {
            segments.add(ElementTypes.TEXT_SEGMENT_TYPE.read(element1));
        }

        return new Text(segments, object.get("size").getAsInt());
    }
}
