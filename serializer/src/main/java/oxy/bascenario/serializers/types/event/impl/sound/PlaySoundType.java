package oxy.bascenario.serializers.types.event.impl.sound;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class PlaySoundType implements TypeWithName<PlaySoundEvent> {
    @Override
    public String type() {
        return "play-sound";
    }

    @Override
    public JsonElement write(PlaySoundEvent event) {
        final JsonObject object = new JsonObject();
        object.add("sound", Types.SOUND_TYPE.write(event.sound()));
        object.addProperty("duration", event.duration());
        object.addProperty("start", event.start());
        return object;
    }

    @Override
    public PlaySoundEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new PlaySoundEvent(Types.SOUND_TYPE.read(object.get("sound")), object.get("duration").getAsLong(), object.get("start").getAsFloat());
    }

    @Override
    public void write(PlaySoundEvent event, ByteBuf buf) {
        Types.SOUND_TYPE.write(event.sound(), buf);
        buf.writeLong(event.duration());
        buf.writeFloat(event.start());
    }

    @Override
    public PlaySoundEvent read(ByteBuf buf) {
        return new PlaySoundEvent(Types.SOUND_TYPE.read(buf), buf.readLong(), buf.readFloat());
    }
}
