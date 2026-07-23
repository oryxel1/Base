package oxy.base.api.managers;

import oxy.base.api.managers.other.Asset;
import oxy.base.api.managers.other.AssetType;
import oxy.base.api.utils.FileInfo;

public interface AssetsManagerApi {
    <T> Asset<T> assets(String scenario, FileInfo info, AssetType type);
    <T> T get(String scenario, FileInfo info, AssetType type);
    void load(String scenario, FileInfo info, AssetType type);

    default <T> Asset<T> assets(String scenario, FileInfo info) {
        return assets(scenario, info, AssetType.UNKNOWN);
    }

    default <T> T get(String scenario, FileInfo info) {
        return get(scenario, info, AssetType.UNKNOWN);
    }

    default <T> Asset<T> assets(FileInfo info, AssetType type) {
        return assets(null, info, type);
    }

    default <T> T get(FileInfo info, AssetType type) {
        return get(null, info, type);
    }

    default <T> Asset<T> assets(FileInfo info) {
        return assets(null, info, AssetType.UNKNOWN);
    }

    default <T> T get(FileInfo info) {
        return get(null, info, AssetType.UNKNOWN);
    }
}
