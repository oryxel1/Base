package oxy.bascenario.api.managers;

import oxy.bascenario.api.utils.FileInfo;

import java.io.File;
import java.io.InputStream;

public interface ScenarioManagerApi {
    InputStream inputStream(String location, FileInfo file);
    File file(String location, FileInfo file);
    String path(String location, FileInfo file);

    void shutdown();
}
