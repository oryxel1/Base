package oxy.bascenario.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.utils.FileInfo;

import java.io.File;

public class FileUtils {
    public static FileHandle toHandle(Scenario scenario, FileInfo info) {
        if (!info.direct() && !info.internal() && scenario != null) {
            File file = new File(Base.instance().getScenarioManager().path(scenario, info));
            return new FileHandle(file.getAbsolutePath());
        }

        return info.internal() ? Gdx.files.internal(info.path()) : new FileHandle(info.path());
    }
}
