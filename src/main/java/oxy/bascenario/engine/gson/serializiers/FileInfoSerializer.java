package oxy.bascenario.engine.gson.serializiers;

import com.google.gson.*;
import oxy.bascenario.engine.base.FileInfo;

import java.lang.reflect.Type;
import java.util.Optional;

public class FileInfoSerializer implements JsonSerializer<FileInfo>, JsonDeserializer<FileInfo> {
    @Override
    public FileInfo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject serialized = jsonElement.getAsJsonObject();
        String path = serialized.get("path").getAsString();
        Optional<String> url = Optional.empty();
        if (serialized.get("download-present").getAsBoolean()) {
            url = Optional.of(serialized.get("download-url").getAsString());
        }
        return new FileInfo(path, url);
    }

    @Override
    public JsonElement serialize(FileInfo fileInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("path", fileInfo.path());
        serialized.addProperty("download-present", fileInfo.url().isPresent());
        if (fileInfo.url().isPresent()) {
            serialized.addProperty("download-url", fileInfo.url().get());
        }
        return serialized;
    }
}
