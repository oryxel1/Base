package oxy.base.api.managers.other;

import oxy.base.api.utils.FileInfo;

public record Asset<T>(String scenario, FileInfo file, T asset) {
}
