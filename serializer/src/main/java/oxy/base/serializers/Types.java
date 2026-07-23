package oxy.base.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import net.lenni0451.commons.color.Color;
import oxy.base.api.Scenario;
import oxy.base.api.Timestamp;
import oxy.base.api.effects.Easing;
import oxy.base.api.effects.Effect;
import oxy.base.api.effects.Sound;
import oxy.base.api.effects.TransitionType;
import oxy.base.api.event.api.Event;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.api.utils.FileInfo;
import oxy.base.serializers.base.Type;
import oxy.base.serializers.types.ColorType;
import oxy.base.serializers.types.effects.SoundType;
import oxy.base.serializers.types.element.ElementTypes;
import oxy.base.serializers.types.element.impl.DialogueType;
import oxy.base.serializers.types.event.EventTypes;
import oxy.base.serializers.types.primitive.*;
import oxy.base.serializers.types.scenario.ScenarioType;
import oxy.base.serializers.types.scenario.TimestampType;
import oxy.base.serializers.types.utils.FileInfoType;
import oxy.base.serializers.types.utils.math.*;
import oxy.base.api.utils.math.Axis;
import oxy.base.api.utils.math.Vec2;
import oxy.base.api.utils.math.Vec3;

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
    });

    public static final Type<String> STRING_TYPE = new StringType();

    public static final Type<FileInfo> FILE_INFO_TYPE = new FileInfoType();
    public static final Type<FileInfo> NULLABLE_FILE_INFO_TYPE = new NullableType<>(FILE_INFO_TYPE);

    public static final Type<Axis> AXIS_TYPE = new EnumType<>(Axis.class, Axis.values());
    public static final Type<Vec2> VECTOR_2F_TYPE = new Vec2Type();
    public static final Type<Vec3> VECTOR_3F_TYPE = new Vec3Type();

    public static final Type<Easing> EASING_TYPE = new EnumType<>(Easing.class, Easing.values());
    public static final Type<Effect> EFFECT_TYPE = new EnumType<>(Effect.class, Effect.values());
    public static final Type<TransitionType> TRANSITION_TYPE_TYPE = new EnumType<>(TransitionType.class, TransitionType.values());
    public static final Type<Sound> SOUND_TYPE = new SoundType();

    public static final Type<RenderLayer> RENDER_LAYER_TYPE = new EnumType<>(RenderLayer.class, RenderLayer.values());

    public static final Type<Color> COLOR_TYPE = new ColorType();
    public static final Type<Color> NULLABLE_COLOR_TYPE = new NullableType<>(COLOR_TYPE);

    public static final Type<Dialogue> DIALOGUE_TYPE = new DialogueType();

    public static final Type<Object> ELEMENT_TYPE = new ElementTypes();
    public static final Type<Event> EVENT_TYPE = new EventTypes();

    public static final Type<Timestamp> TIMESTAMP_TYPE = new TimestampType();
    public static final Type<Scenario> SCENARIO_TYPE = new ScenarioType();
}
