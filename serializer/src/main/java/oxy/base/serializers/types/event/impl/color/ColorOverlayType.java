package oxy.base.serializers.types.event.impl.color;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.color.ColorOverlayEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.TypeWithName;

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
}
