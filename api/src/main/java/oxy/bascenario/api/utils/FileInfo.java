package oxy.bascenario.api.utils;

// Direct is for when the file can be directly access... Shouldn't be used unless for testing purpose.
public record FileInfo(String path, boolean direct, boolean internal) {
    public String path() {
        if (!direct) {
        }

        return this.path;
    }

    public static FileInfo from(String file) {
        return new FileInfo(file, false, false);
    }
}
