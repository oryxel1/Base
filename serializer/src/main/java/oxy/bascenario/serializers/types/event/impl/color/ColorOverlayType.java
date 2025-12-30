package oxy.bascenario.serializers.types.event.impl.color;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class ColorOverlayType implements TypeWithName<ColorOverlayEvent> {
    @Override
    public String type() {
        return "color-overlay";
    }

    @Override
    public JsonElement write(ColorOverlayEvent event) {
        final JsonObject object = new JsonObject();
        object.add("id", Types.NULLABLE_INT.write(event.id().orElse(null)));
        object.addProperty("duration", event.duration());
        object.add("color", Types.COLOR_TYPE.write(event.color()));

        if (event.id().isEmpty()) {
            object.add("layer", Types.RENDER_LAYER_TYPE.write(event.renderLayer()));
        }

        return object;
    }

    @Override
    public ColorOverlayEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final RenderLayer layer;
        if (object.has("layer")) {
            layer = Types.RENDER_LAYER_TYPE.read(object.get("layer"));
        } else {
            layer = null;
        }

        return new ColorOverlayEvent(Types.NULLABLE_INT.read(object.get("id")), object.get("duration").getAsInt(), Types.COLOR_TYPE.read(object.get("color")), layer);
    }

    @Override
    public void write(ColorOverlayEvent event, ByteBuf buf) {
        Types.NULLABLE_INT.write(event.id().orElse(null), buf);
        buf.writeInt(event.duration());
        Types.COLOR_TYPE.write(event.color(), buf);
        if (event.id().isEmpty()) {
            Types.RENDER_LAYER_TYPE.write(event.renderLayer(), buf);
        }
    }

    @Override
    public ColorOverlayEvent read(ByteBuf buf) {
        final Integer id = Types.NULLABLE_INT.read(buf);
        int duration = buf.readInt();
        Color color = Types.COLOR_TYPE.read(buf);
        RenderLayer layer = null;
        if (id == null) {
            layer = Types.RENDER_LAYER_TYPE.read(buf);
        }

        return new ColorOverlayEvent(id, duration, color, layer);
    }
}
