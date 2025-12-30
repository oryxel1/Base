package oxy.bascenario.serializers.types.element.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class SpriteType implements TypeWithName<Sprite> {
    @Override
    public JsonElement write(Sprite sprite) {
        final JsonObject object = new JsonObject();
        object.add("skeleton", Types.FILE_INFO_TYPE.write(sprite.skeleton()));
        object.add("atlas", Types.FILE_INFO_TYPE.write(sprite.atlas()));
        return object;
    }

    @Override
    public Sprite read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new Sprite(Types.FILE_INFO_TYPE.read(object.get("skeleton")), Types.FILE_INFO_TYPE.read(object.get("atlas")));
    }

    @Override
    public void write(Sprite sprite, ByteBuf buf) {
        Types.FILE_INFO_TYPE.write(sprite.skeleton(), buf);
        Types.FILE_INFO_TYPE.write(sprite.atlas(), buf);
    }

    @Override
    public Sprite read(ByteBuf buf) {
        return new Sprite(Types.FILE_INFO_TYPE.read(buf), Types.FILE_INFO_TYPE.read(buf));
    }

    @Override
    public String type() {
        return "sprite";
    }
}
