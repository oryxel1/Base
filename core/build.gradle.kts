plugins {
    id("java")
    id("java-library")
}

var lwjgl = "3.3.6"
var lwjglStd = "3.4.0"
var libGdx = "1.13.1"

repositories {
    maven("https://jitpack.io")
}

dependencies {
    api(project(":api"))
    api(project(":serializer"))

    api(libs.raphimc.thingl)
    api(libs.commons.animations)
    api(libs.unnamed.mocha)

    api(libs.lwjgl.base)
    api(libs.lwjgl.opengl)
    api(libs.lwjgl.stb)
    api(libs.lwjgl.glfw)
    api(libs.lwjgl.sdl)
    api(libs.lwjgl.freetype)
    api(libs.lwjgl.harfbuzz)
    api(libs.lwjgl.meshoptimizer)
    api(libs.lwjgl.par)
    api(libs.lwjgl.nfd)

    api(libs.gdx.base)
    api(libs.gdx.backend)
    api(libs.gdx.box2d)
    api(libs.gdx.spine)

    api(libs.raphimc.audiomixer)

    api(libs.jcraft.jorbis)
    api(libs.javazoom.jlayer)

    api(libs.weisj.jsvg)

    listOf("natives-windows", "natives-windows-arm64", "natives-linux", "natives-linux-arm64").forEach {
        api("org.lwjgl:lwjgl:$lwjgl:$it")
        api("org.lwjgl:lwjgl-glfw:$lwjgl:$it")
        api("org.lwjgl:lwjgl-sdl:$lwjglStd:$it") {
            isTransitive = false
        }
        api("org.lwjgl:lwjgl-opengl:$lwjgl:$it")
        api("org.lwjgl:lwjgl-stb:$lwjgl:$it")
        api("org.lwjgl:lwjgl-freetype:$lwjgl:$it")
        api("org.lwjgl:lwjgl-meshoptimizer:$lwjgl:$it")
        api("org.lwjgl:lwjgl-par:$lwjgl:$it")
        api("org.lwjgl:lwjgl-nfd:$lwjgl:$it")
    }

    api("com.badlogicgames.gdx:gdx-platform:$libGdx:natives-desktop")
    
    api(libs.rivet.core)
    api(libs.rivet.thingl.backend)
}
