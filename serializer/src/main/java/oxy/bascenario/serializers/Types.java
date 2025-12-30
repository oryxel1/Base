package oxy.bascenario.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.effects.*;
import oxy.bascenario.api.utils.math.*;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.types.ColorType;
import oxy.bascenario.serializers.types.effects.SoundType;
import oxy.bascenario.serializers.types.element.ElementTypes;
import oxy.bascenario.serializers.types.element.impl.DialogueType;
import oxy.bascenario.serializers.types.primitive.*;
import oxy.bascenario.serializers.types.utils.FileInfoType;
import oxy.bascenario.serializers.types.utils.math.*;

// I might or might not have steal this format from ViaVersion.
public class Types {
    public static final Type<Integer> NULLABLE_INT = new NullableType<>(new Type<>() {
        @Override
        public JsonElement write(Integer integer) {
            return new JsonPrimitive(integer);
        }

        @Override
        public Integer read(JsonElement element) {
            return element.getAsInt();
        }

        @Override
        public void write(Integer integer, ByteBuf buf) {
            buf.writeInt(integer);
        }

        @Override
        public Integer read(ByteBuf buf) {
            return buf.readInt();
        }
    });

    public static final Type<String> STRING_TYPE = new StringType();

    public static final Type<FileInfo> FILE_INFO_TYPE = new FileInfoType();
    public static final Type<FileInfo> NULLABLE_FILE_INFO_TYPE = new NullableType<>(FILE_INFO_TYPE);

    public static final Type<Axis> AXIS_TYPE = new EnumType<>(Axis.class, Axis.values());
    public static final Type<Vec2> VECTOR_2F_TYPE = new Vec2Type();
    public static final Type<Vec3> VECTOR_3F_TYPE = new Vec3Type();

    public static final Type<Easing> EASING_TYPE = new EnumType<>(Easing.class, Easing.values());
    public static final Type<Effect> EFFECT_TYPE = new EnumType<>(Effect.class, Effect.values());
    public static final Type<Sound> SOUND_TYPE = new SoundType();

    public static final Type<RenderLayer> RENDER_LAYER_TYPE = new EnumType<>(RenderLayer.class, RenderLayer.values());

    public static final Type<Color> COLOR_TYPE = new ColorType();
    public static final Type<Color> NULLABLE_COLOR_TYPE = new NullableType<>(COLOR_TYPE);

    public static final Type<Dialogue> DIALOGUE_TYPE = new DialogueType();

    public static final Type<Object> ELEMENT_TYPE = new ElementTypes();
}
