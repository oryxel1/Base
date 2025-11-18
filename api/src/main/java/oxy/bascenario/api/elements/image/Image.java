package oxy.bascenario.api.elements.image;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.utils.FileInfo;

@RequiredArgsConstructor
@Data
public class Image {
    private final FileInfo file;

    public FileInfo file() {
        return file;
    }
}