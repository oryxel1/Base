package oxy.bascenario.serializers.types.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.enums.OffsetType;
import oxy.bascenario.api.event.dialogue.enums.TextOffset;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.api.render.elements.text.font.Font;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.TextStyle;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.base.Type;
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
import oxy.bascenario.serializers.types.event.impl.dialogue.enums.TextOffsetType;
import oxy.bascenario.serializers.types.primitive.EnumType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ElementTypes implements Type<Object> {
    public static final Type<FontStyle> FONT_STYLE_TYPE = new EnumType<>(FontStyle.class, FontStyle.values());
    public static final Type<FontType> FONT_TYPE_TYPE = new EnumType<>(FontType.class, FontType.values());
    public static final Type<Font> FONT_TYPE = new oxy.bascenario.serializers.types.element.impl.text.font.FontType();

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

    @Override
    public void write(Object o, ByteBuf buf) {
        final TypeWithName<?> type = CLASS_TO_TYPE.get(o.getClass());
        if (type == null) {
            throw new RuntimeException("Invalid element class type: " + o.getClass() + "!");
        }

        buf.writeInt(type.type().hashCode());
        type.writeElement(o, buf);
    }

    @Override
    public Object read(ByteBuf buf) {
        final int id = buf.readInt();
        final TypeWithName<?> type = ID_TO_TYPE.get(id);
        if (type == null) {
            throw new RuntimeException("Invalid element with id: " + id + "!");
        }

        return type.read(buf);
    }
}
