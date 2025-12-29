package oxy.bascenario.serializers.types.element.impl.image;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.types.element.ElementType;

public class ImageType implements ElementType<Image> {
    @Override
    public String type() {
        return "image";
    }

    @Override
    public JsonElement write(Image image) {
        final JsonObject object = new JsonObject();
        object.add("file", Types.NULLABLE_FILE_INFO_TYPE.write(image.file())); // not actually optional butttt it could be null.
        object.add("color", Types.COLOR_TYPE.write(image.color()));

        final JsonArray array = new JsonArray();
        array.add(image.width());
        array.add(image.height());
        object.add("size", array);
        return object;
    }

    @Override
    public Image read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final JsonArray size = object.getAsJsonArray("size");
        return new Image(Types.NULLABLE_FILE_INFO_TYPE.read(object.get("file")), Types.COLOR_TYPE.read(object.get("color")), size.get(0).getAsInt(), size.get(1).getAsInt());
    }

    @Override
    public void write(Image image, ByteBuf buf) {
        Types.NULLABLE_FILE_INFO_TYPE.write(image.file(), buf);
        Types.COLOR_TYPE.write(image.color(), buf);
        buf.writeInt(image.width());
        buf.writeInt(image.height());
    }

    @Override
    public Image read(ByteBuf buf) {
        return new Image(Types.NULLABLE_FILE_INFO_TYPE.read(buf), Types.COLOR_TYPE.read(buf), buf.readInt(), buf.readInt());
    }
}
