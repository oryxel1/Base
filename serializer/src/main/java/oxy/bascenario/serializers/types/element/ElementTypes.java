package oxy.bascenario.serializers.types.element;

import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.serializers.Type;
import oxy.bascenario.serializers.types.element.impl.text.*;
import oxy.bascenario.serializers.types.primitive.EnumType;

import java.util.Set;

public class ElementTypes {
    public static final Type<FontType> FONT_TYPE_TYPE = new EnumType<>(FontType.class, FontType.values());

//    public static final Type<TextStyle> TEXT_STYLE_TYPE = new EnumType<>(TextStyle.class, TextStyle.values());
    public static final Type<Set<TextStyle>> TEXT_STYLES_TYPE = new TextStylesType();
    public static final Type<TextSegment> TEXT_SEGMENT_TYPE = new TextSegmentType();

    public static final Type<Text> TEXT_TYPE = new TextType();
}
