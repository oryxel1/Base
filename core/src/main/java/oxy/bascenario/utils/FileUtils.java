package oxy.bascenario.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import oxy.bascenario.api.utils.FileInfo;

public class FileUtils {
    public static FileHandle toHandle(FileInfo info) {
        return info.internal() ? Gdx.files.internal(info.path()) : new FileHandle(info.path());
    }
}
