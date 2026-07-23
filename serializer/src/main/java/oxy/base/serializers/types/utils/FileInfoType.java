package oxy.base.serializers.types.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.utils.FileInfo;
import oxy.base.serializers.base.Type;

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
}
