package oxy.bascenario.api.utils;

import java.util.Objects;

// Direct is for when the file can be directly access... Shouldn't be used unless for testing purpose.
public record FileInfo(String path, boolean direct, boolean internal) {
    public static FileInfo from(String file) {
        return new FileInfo(file, false, false);
    }

    public int hashCode(String scenario) {
        return Objects.hash(scenario, path, direct, internal);
    }
}
