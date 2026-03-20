package oxy.bascenario.serializers.types.element.impl.text;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.serializers.base.Type;

import java.util.EnumSet;
import java.util.Set;

public class TextStylesType implements Type<Set<TextStyle>> {
    @Override
    public JsonElement write(Set<TextStyle> textStyles) {
        final JsonArray array = new JsonArray();
        for (TextStyle style : textStyles) {
            array.add(style.name());
        }

        return array;
    }

    @Override
    public Set<TextStyle> read(JsonElement element) {
        final Set<TextStyle> styles = EnumSet.noneOf(TextStyle.class);
        final JsonArray array = element.getAsJsonArray();
        for (JsonElement element1 : array) {
            styles.add(TextStyle.valueOf(element1.getAsString()));
        }

        return styles;
    }
}
