package oxy.bascenario.utils;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.nfd.NFDFilterItem;
import org.lwjgl.util.nfd.NativeFileDialog;

import java.nio.ByteBuffer;
import java.util.Locale;

import static org.lwjgl.util.nfd.NativeFileDialog.NFD_OKAY;

public class NFDUtils {
    public static String pickFolder() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer outPath = stack.mallocPointer(1);

            int result = NativeFileDialog.NFD_PickFolder(outPath, stack.UTF8(System.getProperty("user.home")));
            if (result == NFD_OKAY) {
                return outPath.getStringUTF8(0).toLowerCase(Locale.ROOT);
            }
        }

        return "";
    }

    public static String pickFile(String extensions) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer outPath = stack.mallocPointer(1);

            NFDFilterItem.Buffer filters = NFDFilterItem.malloc(1);
            filters.get(0).name(stack.UTF8(extensions)).spec(stack.UTF8(extensions));

            int result = NativeFileDialog.NFD_OpenDialog(outPath, filters, (ByteBuffer) null);
            if (result == NFD_OKAY) {
                String filePath = outPath.getStringUTF8(0).toLowerCase(Locale.ROOT);
                boolean valid = extensions.equals("*");
                if (!valid) {
                    for (String ext : extensions.replace(" ", "").split(",")) {
                        if (filePath.endsWith("." + ext)) {
                            valid = true;
                            break;
                        }
                    }
                }

                if (!valid) {
                    return "";
                }

                NativeFileDialog.nNFD_FreePath(outPath.get(0));
                return filePath;
            }
        }

        return "";
    }
}
