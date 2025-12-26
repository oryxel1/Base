package oxy.bascenario.api.managers.other;

import oxy.bascenario.api.utils.FileInfo;

public record Asset<T>(String scenario, FileInfo file, T asset) {
}
