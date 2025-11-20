package oxy.bascenario.api.elements;

import oxy.bascenario.api.utils.FileInfo;

// Well even tho the image file info will never get used, it is actually *used* for downloading
// the files if needed to....
public record Sprite(FileInfo skeleton, FileInfo atlas, FileInfo image) {
}