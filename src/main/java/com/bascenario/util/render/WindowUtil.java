package com.bascenario.util.render;

import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class WindowUtil {
    public static void setFullScreen(long handle, boolean fullScreen) {
        if (fullScreen) {
            float[] x1 = new float[1];
            float[] y1 = new float[1];
            GLFW.glfwGetMonitorContentScale(GLFW.glfwGetPrimaryMonitor(), x1, y1);
            final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            GLFW.glfwSetWindowMonitor(handle, GLFW.glfwGetPrimaryMonitor(), (int) x1[0], (int) y1[0], dimension.width, dimension.height, GLFW_DONT_CARE);
        } else {
            GLFW.glfwSetWindowMonitor(handle, 0L, 0, 0, 0, 0, GLFW_DONT_CARE);
        }
    }
}
