package oxy.bascenario.api.managers.other;

import oxy.bascenario.api.utils.FileInfo;

public record Asset<T>(FileInfo file, T asset) {
}
