package oxy.bascenario.serializers.types.element.impl.text;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.types.element.ElementTypes;

public class TextSegmentType implements Type<TextSegment> {
    @Override
    public JsonElement write(TextSegment textSegment) {
        final JsonObject object = new JsonObject();
        object.addProperty("text", textSegment.text());
        object.add("font", ElementTypes.FONT_TYPE.write(textSegment.font()));
        object.add("color", Types.COLOR_TYPE.write(textSegment.color()));
        object.add("outline", Types.NULLABLE_COLOR_TYPE.write(textSegment.outline().orElse(null)));
        object.add("style", ElementTypes.TEXT_STYLES_TYPE.write(textSegment.styles()));

        return object;
    }

    @Override
    public TextSegment read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final TextSegment.Builder builder = TextSegment.builder().text(object.get("text").getAsString());
        builder.font(ElementTypes.FONT_TYPE.read(object.get("font")));
        builder.color(Types.COLOR_TYPE.read(object.get("color")));
        builder.outline(Types.NULLABLE_COLOR_TYPE.read(object.get("outline")));
        builder.styles().addAll(ElementTypes.TEXT_STYLES_TYPE.read(object.get("style")));
        return builder.build();
    }

    @Override
    public void write(TextSegment textSegment, ByteBuf buf) {
        Types.STRING_TYPE.write(textSegment.text(), buf);
        ElementTypes.FONT_TYPE.write(textSegment.font(), buf);
        Types.COLOR_TYPE.write(textSegment.color(), buf);
        Types.NULLABLE_COLOR_TYPE.write(textSegment.outline().orElse(null), buf);
        ElementTypes.TEXT_STYLES_TYPE.write(textSegment.styles(), buf);
    }

    @Override
    public TextSegment read(ByteBuf buf) {
        final TextSegment.Builder builder = TextSegment.builder().text(Types.STRING_TYPE.read(buf));
        builder.font(ElementTypes.FONT_TYPE.read(buf));
        builder.color(Types.COLOR_TYPE.read(buf));
        builder.outline(Types.NULLABLE_COLOR_TYPE.read(buf));
        builder.styles().addAll(ElementTypes.TEXT_STYLES_TYPE.read(buf));
        return builder.build();
    }
}
