package oxy.bascenario.api.managers;

import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.api.utils.FileInfo;

public interface AssetsManagerApi {
    <T> Asset<T> assets(String scenario, FileInfo info);
    <T> T get(String scenario, FileInfo info);
    void load(String scenario, FileInfo info);

    default <T> Asset<T> assets(FileInfo info) {
        return assets(null, info);
    }

    default <T> T get(FileInfo info) {
        return get(null, info);
    }
}
