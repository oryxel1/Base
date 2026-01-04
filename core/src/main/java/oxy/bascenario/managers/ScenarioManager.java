package oxy.bascenario.managers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.SneakyThrows;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.managers.ScenarioManagerApi;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.utils.GsonUtils;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ScenarioManager implements ScenarioManagerApi {
    public static final File SAVE_DIR = new File(Base.SAVE_DIR, "scenarios");

    private final Map<String, Scenario> scenarios = new HashMap<>();
    public void put(String name, Scenario scenario) {
        this.scenarios.put(name, scenario);
    }
    public Collection<Scenario> scenarios() {
        return scenarios.values();
    }

    @SneakyThrows
    public ScenarioManager() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }

        final DirectoryStream<Path> stream = Files.newDirectoryStream(SAVE_DIR.toPath(), Files::isDirectory);
        for (Path entry : stream) {
            final File file = new File(entry.toFile(), "scenario.base");
            if (file.isDirectory() || !file.exists()) {
                continue;
            }

            final byte[] bytes = Files.readAllBytes(file.toPath());
            try {
                JsonObject object = JsonParser.parseString(new String(bytes)).getAsJsonObject();
                final Scenario scenario = Types.SCENARIO_TYPE.read(object);
                scenarios.put(scenario.getName(), scenario);
            } catch (Exception ignored) {
                ByteBuf buf = Unpooled.buffer().writeBytes(bytes);
                try {
                    final Scenario scenario = Types.SCENARIO_TYPE.read(buf);
                    scenarios.put(scenario.getName(), scenario);
                } catch (Exception ignored1) {
                } finally {
                    buf.release();
                }
            }
        }
    }

    @SneakyThrows
    public InputStream inputStream(String location, FileInfo file) {
        if (location == null || file == null) {
            return null;
        }

        if (file.direct()) {
            return new FileInputStream(new File(file.path()));
        } else if (file.internal()) {
            return ScenarioManager.class.getResourceAsStream("/" + file.path());
        }

        return new FileInputStream(file(location, file));
    }

    public File file(String location, FileInfo file) {
        if (file == null) {
            return null;
        }

        if (file.direct() || file.internal() || location == null) {
            return new File(file.path());
        }

        File file1 = new File(new File(SAVE_DIR, location), "files");
        File result = new File(file1, file.path());
        new File(result.getAbsolutePath().replace(result.getName(), "")).mkdirs();
        return result;
    }

    public String path(String location, FileInfo file) {
        if (file == null || file.direct() || file.internal() || location == null) {
            return file.path();
        }

        return file(location, file).getAbsolutePath();
    }

    public void shutdown() {
        for (Scenario scenario : scenarios.values()) {
            try {
                saveToPath(scenario);
            } catch (Exception ignored) {
            }
        }
    }

    public void saveToPath(Scenario scenario) throws Exception {
        final File path = new File(SAVE_DIR, scenario.getName());
        if (path.exists() && !path.isDirectory()) {
            path.delete();
        }
        path.mkdirs();

        final File save = new File(path, "scenario.base");
        if (scenario.getSaveType() == Scenario.SaveType.JSON) {
            try (FileWriter writer = new FileWriter(save)) {
                GsonUtils.getGson().toJson(Types.SCENARIO_TYPE.write(scenario), writer);
            }
        } else {
            final ByteBuf buf = Unpooled.buffer();
            try {
                Types.SCENARIO_TYPE.write(scenario, buf);
                final byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);

                Files.write(save.toPath(), bytes);
            } finally {
                buf.release();
            }
        }
    }
}
