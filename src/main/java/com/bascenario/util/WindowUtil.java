package com.bascenario.util;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;

public class WindowUtil {
    public static void setFullScreen(long handle, boolean fullScreen) {
        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if (fullScreen && mode != null) {
            glfwSetWindowMonitor(handle, glfwGetPrimaryMonitor(), 0, 0, mode.width(), mode.height(), mode.refreshRate());
        } else {
            glfwSetWindowMonitor(handle, 0L, 0, 0, 0, 0, GLFW_DONT_CARE);
        }
    }
}
