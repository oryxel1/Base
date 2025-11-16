package oxy.bascenario.engine.base;

import java.util.Optional;

// Url is here so if the scenario ever get shared, this will automatically download the assets.
// If url is present, then the path will be the path inside the where BASE decide to save stuff.
public record FileInfo(String path, Optional<String> url) {
}
