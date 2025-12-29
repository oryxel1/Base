package oxy.bascenario.serializers.types.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.serializers.Type;
import oxy.bascenario.serializers.Types;

public class FileInfoType implements Type<FileInfo> {
    @Override
    public JsonElement write(FileInfo fileInfo) {
        final JsonObject object = new JsonObject();
        object.addProperty("path", fileInfo.path());
        object.addProperty("direct", fileInfo.direct());
        object.addProperty("internal", fileInfo.internal());
        return object;
    }

    @Override
    public FileInfo read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new FileInfo(object.get("path").getAsString(), object.get("direct").getAsBoolean(), object.get("internal").getAsBoolean());
    }

    @Override
    public void write(FileInfo fileInfo, ByteBuf buf) {
        Types.STRING_TYPE.write(fileInfo.path(), buf);
        Types.BOOLEAN_TYPE.write(fileInfo.direct(), buf);
        Types.BOOLEAN_TYPE.write(fileInfo.internal(), buf);
    }

    @Override
    public FileInfo read(ByteBuf buf) {
        return new FileInfo(Types.STRING_TYPE.read(buf), Types.BOOLEAN_TYPE.read(buf), Types.BOOLEAN_TYPE.read(buf));
    }
}
