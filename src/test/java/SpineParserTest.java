import com.bascenario.util.SkeletonUtil;
import com.esotericsoftware.spine.SkeletonData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.IOException;

public class SpineParserTest {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    @SneakyThrows
    public static void main(String[] args) {
        SkeletonData data = SkeletonUtil.parse("C:\\Users\\PC\\Downloads\\hina_spr.skel");

        try (FileWriter writer = new FileWriter("run/output.json")) {
            GSON.toJson(data, writer);
        } catch (IOException ignored) {
        }
    }
}
