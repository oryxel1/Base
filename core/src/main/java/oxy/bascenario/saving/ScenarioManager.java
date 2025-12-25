package oxy.bascenario.saving;

import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.utils.FileInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ScenarioManager {
    private static final File SAVE_DIR = new File(Base.SAVE_DIR, "scenarios");

    private final Map<String, Scenario> scenarios = new HashMap<>();

    public ScenarioManager() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }
    }

    public String path(Scenario scenario, FileInfo file) {
        if (file.direct() || file.internal() || scenario.getLocation() == null) {
            return file.path();
        }

        File file1 = new File(new File(SAVE_DIR, scenario.getLocation()), "files");
        file1.mkdirs();
        return new File(file1, file.path()).getAbsolutePath();
    }

    public String path(Scenario.Builder scenario, FileInfo file) {
        if (file.direct() || file.internal() || scenario.location() == null) {
            return file.path();
        }

        File file1 = new File(new File(SAVE_DIR, scenario.location()), "files");
        file1.mkdirs();
        return new File(file1, file.path()).getAbsolutePath();
    }

    public void shutdown() {
    }
}
