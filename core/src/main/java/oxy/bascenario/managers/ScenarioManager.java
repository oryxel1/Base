package oxy.bascenario.managers;

import lombok.SneakyThrows;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.managers.ScenarioManagerApi;
import oxy.bascenario.api.utils.FileInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ScenarioManager implements ScenarioManagerApi {
    public static final File SAVE_DIR = new File(Base.SAVE_DIR, "scenarios");

    private final Map<String, Scenario> scenarios = new HashMap<>();
    public Collection<Scenario> scenarios() {
        return scenarios.values();
    }

    public ScenarioManager() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }
    }

    @SneakyThrows
    public InputStream inputStream(String location, FileInfo file) {
        if (location == null) {
            return null;
        }

        if (file.direct()) {
            return new FileInputStream(new File(file.path()));
        } else if (file.internal()) {
            return ScenarioManager.class.getResourceAsStream("/" + file.path());
        }

        File file1 = new File(new File(SAVE_DIR, location), "files");
        file1.mkdirs();
        return new FileInputStream(new File(file1, file.path()));
    }

    public File file(String location, FileInfo file) {
        if (file == null) {
            return null;
        }

        if (file.direct() || file.internal() || location == null) {
            return new File(file.path());
        }

        File file1 = new File(new File(SAVE_DIR, location), "files");
        file1.mkdirs();
        return new File(file1, file.path());
    }

    public String path(String location, FileInfo file) {
        if (file.direct() || file.internal() || location == null) {
            return file.path();
        }

        File file1 = new File(new File(SAVE_DIR, location), "files");
        file1.mkdirs();
        return new File(file1, file.path()).getAbsolutePath();
    }

    public void shutdown() {
    }
}
