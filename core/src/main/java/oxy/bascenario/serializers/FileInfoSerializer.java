package oxy.bascenario.serializers;

import com.google.gson.*;
import oxy.bascenario.api.utils.FileInfo;

import java.lang.reflect.Type;
import java.util.Optional;

public class FileInfoSerializer implements JsonSerializer<FileInfo>, JsonDeserializer<FileInfo> {
    @Override
    public FileInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject serialized = jsonElement.getAsJsonObject();
        String path = serialized.get("path").getAsString();
        return new FileInfo(path, serialized.get("direct").getAsBoolean(), serialized.get("internal").getAsBoolean());
    }

    @Override
    public JsonElement serialize(FileInfo fileInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("path", fileInfo.path());
        serialized.addProperty("direct", fileInfo.direct());
        serialized.addProperty("internal", fileInfo.internal());
        return serialized;
    }
}
