package oxy.bascenario.serializers.types.effects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.Types;

public class SoundType implements Type<Sound> {
    @Override
    public JsonElement write(Sound sound) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", sound.id());
        object.add("file", Types.NULLABLE_FILE_INFO_TYPE.write(sound.file()));
        object.addProperty("max-volume", sound.maxVolume());
        object.addProperty("loop", sound.loop()); // tbh this is never used in the editor itself buuuuuuuuut.
        return object;
    }

    @Override
    public Sound read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new Sound(object.get("id").getAsInt(), Types.NULLABLE_FILE_INFO_TYPE.read(object.get("file")), object.get("max-volume").getAsFloat(), object.get("loop").getAsBoolean());
    }

    @Override
    public void write(Sound sound, ByteBuf buf) {
        buf.writeInt(sound.id());
        Types.NULLABLE_FILE_INFO_TYPE.write(sound.file(), buf);
        buf.writeFloat(sound.maxVolume());
        buf.writeBoolean(sound.loop());
    }

    @Override
    public Sound read(ByteBuf buf) {
        return new Sound(buf.readInt(), Types.NULLABLE_FILE_INFO_TYPE.read(buf), buf.readFloat(), buf.readBoolean());
    }
}
