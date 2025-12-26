plugins {
    id("java")
}

var lwjgl = "3.3.6"
var lwjglStd = "3.4.0-SNAPSHOT"
var libGdx = "1.13.1"

dependencies {
    implementation(project(":api"))

    implementation(libs.raphimc.thingl)
    implementation(libs.commons.animations)
    implementation(libs.unnamed.mocha)

    implementation(libs.imgui.lwjgl3)
    implementation(libs.imgui.binding)
    implementation(libs.imgui.natives.windows)
    implementation(libs.imgui.natives.linux)
    implementation(libs.imgui.natives.macos)

    implementation(libs.lwjgl.base)
    implementation(libs.lwjgl.opengl)
    implementation(libs.lwjgl.stb)
    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.sdl)
    implementation(libs.lwjgl.freetype)
    implementation(libs.lwjgl.harfbuzz)
    implementation(libs.lwjgl.meshoptimizer)
    implementation(libs.lwjgl.par)
    implementation(libs.lwjgl.nfd)

    implementation(libs.gdx.base)
    implementation(libs.gdx.backend)
    implementation(libs.gdx.box2d)
    implementation(libs.gdx.spine)

    implementation(libs.raphimc.audiomixer)

    implementation(libs.jcraft.jorbis)
    implementation(libs.javazoom.jlayer)

    listOf("natives-windows", "natives-windows-arm64", "natives-linux", "natives-linux-arm64").forEach {
        implementation("org.lwjgl:lwjgl:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-glfw:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-sdl:$lwjglStd:$it") {
            isTransitive = false
        }
        implementation("org.lwjgl:lwjgl-opengl:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-stb:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-freetype:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-meshoptimizer:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-par:$lwjgl:$it")
        implementation("org.lwjgl:lwjgl-nfd:$lwjgl:$it")
    }

    implementation("com.badlogicgames.gdx:gdx-platform:$libGdx:natives-desktop")
}