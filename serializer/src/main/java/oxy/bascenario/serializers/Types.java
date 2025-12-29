package oxy.bascenario.serializers;

import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.*;
import oxy.bascenario.serializers.types.primitive.*;
import oxy.bascenario.serializers.types.utils.FileInfoType;
import oxy.bascenario.serializers.types.utils.math.*;

// I might or might not have steal this format from ViaVersion.
public class Types {
    public static final Type<String> STRING_TYPE = new StringType();
    public static final Type<Boolean> BOOLEAN_TYPE = new BooleanType();
    public static final Type<Integer> INT_TYPE = new IntType();
    public static final Type<Long> LONG_TYPE = new LongType();
    public static final Type<Float> FLOAT_TYPE = new FloatType();

    public static final Type<FileInfo> FILE_INFO_TYPE = new FileInfoType();
    public static final Type<Axis> AXIS_TYPE = new AxisType();
    public static final Type<Vec2> VECTOR_2F_TYPE = new Vec2Type();
    public static final Type<Vec3> VECTOR_3F_TYPE = new Vec3Type();
}
