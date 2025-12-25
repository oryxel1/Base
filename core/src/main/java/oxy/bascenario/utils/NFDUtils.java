package oxy.bascenario.utils;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.nfd.NativeFileDialog;

import java.nio.ByteBuffer;
import java.util.Locale;

import static org.lwjgl.util.nfd.NativeFileDialog.NFD_OKAY;

public class NFDUtils {
    public static String pickFile(String... extensions) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer outPath = stack.mallocPointer(1);

            int result = NativeFileDialog.NFD_OpenDialog(outPath, null, (ByteBuffer) null);
            if (result == NFD_OKAY) {
                String filePath = outPath.getStringUTF8(0).toLowerCase(Locale.ROOT);
                boolean valid = false;
                for (String ext : extensions) {
                    if (filePath.endsWith("." + ext)) {
                        valid = true;
                        break;
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
