package oxy.bascenario.utils.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import oxy.bascenario.Base;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.ScenarioManager;
import oxy.bascenario.serializers.Types;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileUtils {
    public static FileInfo combine(FileInfo first, FileInfo second) {
        final JsonObject object = new JsonObject();
        object.add("first", Types.FILE_INFO_TYPE.write(first));
        object.add("second", Types.FILE_INFO_TYPE.write(second));

        return new FileInfo(object.toString(), false, false);
    }

    @SneakyThrows
    public static InputStream toStream(String scenario, FileInfo info) {
        if (scenario != null) {
            final InputStream stream = Base.instance().scenarioManager().inputStream(scenario, info);
            if (stream != null) {
                return stream;
            }
        }

        return info.internal() ? ScenarioManager.class.getResourceAsStream("/" + info.path()) : new FileInputStream(info.path());
    }

    public static FileHandle toHandle(String scenario, FileInfo info) {
        if (!info.direct() && !info.internal() && scenario != null) {
            File file = new File(Base.instance().scenarioManager().path(scenario, info));
            return new FileHandle(file.getAbsolutePath());
        }

        return info.internal() ? Gdx.files.internal(info.path()) : new FileHandle(info.path());
    }
}
