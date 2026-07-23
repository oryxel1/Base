package oxy.base.serializers.types.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.dialogue.enums.OffsetType;
import oxy.base.api.event.dialogue.enums.TextOffset;
import oxy.base.api.render.elements.LocationInfo;
import oxy.base.api.render.elements.Preview;
import oxy.base.api.render.elements.Sprite;
import oxy.base.api.render.elements.emoticon.Emoticon;
import oxy.base.api.render.elements.image.AnimatedImage;
import oxy.base.api.render.elements.image.Image;
import oxy.base.api.render.elements.shape.Circle;
import oxy.base.api.render.elements.shape.Rectangle;
import oxy.base.api.render.elements.shape.Triangle;
import oxy.base.api.render.elements.text.AnimatedText;
import oxy.base.api.render.elements.text.font.Font;
import oxy.base.api.render.elements.text.font.FontStyle;
import oxy.base.api.render.elements.text.Text;
import oxy.base.api.render.elements.text.TextSegment;
import oxy.base.api.render.elements.text.TextStyle;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.serializers.base.TypeWithName;
import oxy.base.serializers.base.Type;
import oxy.base.serializers.types.element.impl.LocationInfoType;
import oxy.base.serializers.types.element.impl.PreviewType;
import oxy.base.serializers.types.element.impl.SpriteType;
import oxy.base.serializers.types.element.impl.emoticon.EmoticonType;
import oxy.base.serializers.types.element.impl.image.AnimatedImageType;
import oxy.base.serializers.types.element.impl.image.ImageType;
import oxy.base.serializers.types.element.impl.shapes.CircleType;
import oxy.base.serializers.types.element.impl.shapes.RectangleType;
import oxy.base.serializers.types.element.impl.shapes.TriangleType;
import oxy.base.serializers.types.element.impl.text.*;
import oxy.base.serializers.types.event.impl.dialogue.enums.TextOffsetType;
import oxy.base.serializers.types.primitive.EnumType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElementTypes implements Type<Object> {
    public static final Type<FontStyle> FONT_STYLE_TYPE = new EnumType<>(FontStyle.class, FontStyle.values());
    public static final Type<FontType> FONT_TYPE_TYPE = new EnumType<>(FontType.class, FontType.values());
    public static final Type<Font> FONT_TYPE = new oxy.base.serializers.types.element.impl.text.font.FontType();

    public static final Type<OffsetType> OFFSET_TYPE = new EnumType<>(OffsetType.class, OffsetType.values());
    public static final Type<TextOffset> TEXT_OFFSET_TYPE = new TextOffsetType();

    public static final Type<Set<TextStyle>> TEXT_STYLES_TYPE = new TextStylesType();
    public static final Type<TextSegment> TEXT_SEGMENT_TYPE = new TextSegmentType();

    public static final TypeWithName<Text> TEXT_TYPE = new TextType();

    private static final Map<Class<?>, TypeWithName<?>> CLASS_TO_TYPE = new HashMap<>();
    private static final Map<Integer, TypeWithName<?>> ID_TO_TYPE = new HashMap<>();
    private static void put(Class<?> klass, TypeWithName<?> type) {
        CLASS_TO_TYPE.put(klass, type);
        ID_TO_TYPE.put(type.type().hashCode(), type);
    }

    static {
        put(Text.class, TEXT_TYPE);
        put(AnimatedText.class, new AnimatedTextType());
        put(Sprite.class, new SpriteType());
        put(Preview.class, new PreviewType());
        put(LocationInfo.class, new LocationInfoType());

        put(Circle.class, new CircleType());
        put(Rectangle.class, new RectangleType());
        put(Triangle.class, new TriangleType());

        put(Image.class, new ImageType());
        put(AnimatedImage.class, new AnimatedImageType());

        put(Emoticon.class, new EmoticonType());
    }

    @Override
    public JsonElement write(Object o) {
        final TypeWithName<?> type = CLASS_TO_TYPE.get(o.getClass());
        if (type == null) {
            throw new RuntimeException("Invalid element class type: " + o.getClass() + "!");
        }

        final JsonObject object = new JsonObject();
        object.addProperty("type", type.type());
        object.add("element", type.writeElement(o));
        return object;
    }

    @Override
    public Object read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final int id = object.get("type").getAsString().hashCode();
        final TypeWithName<?> type = ID_TO_TYPE.get(id);
        if (type == null) {
            throw new RuntimeException("Invalid element with id: " + id + "!");
        }

        return type.read(object.get("element"));
    }
}
