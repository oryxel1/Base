package oxy.bascenario.api.utils;

import java.util.Optional;

// Url is here so if the scenario ever get shared, this will automatically download the assets.
// If url is present, then the path will be the path inside the where BASE decide to save stuff.
public record FileInfo(String path, Optional<String> url, boolean internal) {
    public String path() {
        // TODO: Make this return the full path, in case the url is there...
        if (url.isPresent()) {
            // Nothing here for now.
        }

        return this.path;
    }

    public static FileInfo from(String file) {
        return new FileInfo(file, Optional.empty(), false);
    }
}
