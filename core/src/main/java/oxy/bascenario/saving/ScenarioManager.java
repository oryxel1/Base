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

    private final Map<Scenario, String> scenarios = new HashMap<>();

    public ScenarioManager() {
        if (!SAVE_DIR.isDirectory()) {
            SAVE_DIR.mkdirs();
        }
    }

    public String path(Scenario scenario, FileInfo file) {
        if (file.direct() || file.internal()) {
            return file.path();
        }

        final String path = scenarios.get(scenario);
        if (path == null) {
            return file.path();
        }

        return new File(SAVE_DIR, path).getAbsolutePath();
    }
}
