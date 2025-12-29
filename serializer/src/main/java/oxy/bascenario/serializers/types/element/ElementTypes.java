package oxy.bascenario.serializers.types.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.serializers.Type;
import oxy.bascenario.serializers.types.element.impl.LocationInfoType;
import oxy.bascenario.serializers.types.element.impl.PreviewType;
import oxy.bascenario.serializers.types.element.impl.SpriteType;
import oxy.bascenario.serializers.types.element.impl.emoticon.EmoticonType;
import oxy.bascenario.serializers.types.element.impl.image.AnimatedImageType;
import oxy.bascenario.serializers.types.element.impl.image.ImageType;
import oxy.bascenario.serializers.types.element.impl.shapes.CircleType;
import oxy.bascenario.serializers.types.element.impl.shapes.RectangleType;
import oxy.bascenario.serializers.types.element.impl.shapes.TriangleType;
import oxy.bascenario.serializers.types.element.impl.text.*;
import oxy.bascenario.serializers.types.primitive.EnumType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElementTypes implements Type<Object> {
    public static final Type<FontType> FONT_TYPE_TYPE = new EnumType<>(FontType.class, FontType.values());

//    public static final Type<TextStyle> TEXT_STYLE_TYPE = new EnumType<>(TextStyle.class, TextStyle.values());
    public static final Type<Set<TextStyle>> TEXT_STYLES_TYPE = new TextStylesType();
    public static final Type<TextSegment> TEXT_SEGMENT_TYPE = new TextSegmentType();

    public static final ElementType<Text> TEXT_TYPE = new TextType();
//    public static final ElementType<Sprite> SPRITE_TYPE = new SpriteType();
//    public static final ElementType<Preview> PREVIEW_TYPE = new PreviewType();
//    public static final ElementType<LocationInfo> LOCATION_INFO_TYPE = new LocationInfoType();
//    public static final ElementType<Circle> CIRCLE_TYPE = new CircleType();
//    public static final ElementType<Rectangle> RECTANGLE_TYPE = new RectangleType();
//    public static final ElementType<Triangle> TRIANGLE_TYPE = new TriangleType();
//    public static final ElementType<Image> IMAGE_TYPE = new ImageType();
//    public static final ElementType<AnimatedImage> ANIMATED_IMAGE_TYPE = new AnimatedImageType();
//    public static final ElementType<Emoticon> EMOTICON_TYPE = new EmoticonType();

    private static final Map<Class<?>, ElementType<?>> CLASS_TO_TYPE = new HashMap<>();
    private static final Map<Integer, ElementType<?>> ID_TO_TYPE = new HashMap<>();
    private static void put(Class<?> klass, ElementType<?> type) {
        CLASS_TO_TYPE.put(klass, type);
        ID_TO_TYPE.put(type.type().hashCode(), type);
    }

    static {
        put(Text.class, TEXT_TYPE);
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
        final ElementType<?> type = CLASS_TO_TYPE.get(o.getClass());
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
        final ElementType<?> type = ID_TO_TYPE.get(id);
        if (type == null) {
            throw new RuntimeException("Invalid element with id: " + id + "!");
        }

        return type.read(element);
    }

    @Override
    public void write(Object o, ByteBuf buf) {
        final ElementType<?> type = CLASS_TO_TYPE.get(o.getClass());
        if (type == null) {
            throw new RuntimeException("Invalid element class type: " + o.getClass() + "!");
        }

        buf.writeInt(type.type().hashCode());
        type.writeElement(o, buf);
    }

    @Override
    public Object read(ByteBuf buf) {
        final int id = buf.readInt();
        final ElementType<?> type = ID_TO_TYPE.get(id);
        if (type == null) {
            throw new RuntimeException("Invalid element with id: " + id + "!");
        }

        return type.read(buf);
    }
}
