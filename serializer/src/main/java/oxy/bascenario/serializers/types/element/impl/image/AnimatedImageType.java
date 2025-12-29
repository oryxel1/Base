package oxy.bascenario.serializers.types.element.impl.image;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.types.element.ElementType;

public class AnimatedImageType implements ElementType<AnimatedImage> {
    @Override
    public String type() {
        return "animated-image";
    }

    @Override
    public JsonElement write(AnimatedImage image) {
        final JsonObject object = new JsonObject();
        object.add("file", Types.NULLABLE_FILE_INFO_TYPE.write(image.file())); // not actually optional butttt it could be null.
        object.addProperty("start", image.start());
        object.addProperty("loop", image.loop());
        object.add("color", Types.COLOR_TYPE.write(image.color()));

        final JsonArray array = new JsonArray();
        array.add(image.width());
        array.add(image.height());
        object.add("size", array);
        return object;
    }

    @Override
    public AnimatedImage read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final JsonArray size = object.getAsJsonArray("size");
        return new AnimatedImage(Types.NULLABLE_FILE_INFO_TYPE.read(object.get("file")), object.get("start").getAsLong(), object.get("loop").getAsBoolean(), Types.COLOR_TYPE.read(object.get("color")), size.get(0).getAsInt(), size.get(1).getAsInt());
    }

    @Override
    public void write(AnimatedImage image, ByteBuf buf) {
        Types.NULLABLE_FILE_INFO_TYPE.write(image.file(), buf);
        buf.writeLong(image.start());
        buf.writeBoolean(image.loop());
        Types.COLOR_TYPE.write(image.color(), buf);
        buf.writeInt(image.width());
        buf.writeInt(image.height());
    }

    @Override
    public AnimatedImage read(ByteBuf buf) {
        return new AnimatedImage(Types.NULLABLE_FILE_INFO_TYPE.read(buf), buf.readLong(), buf.readBoolean(), Types.COLOR_TYPE.read(buf), buf.readInt(), buf.readInt());
    }
}
