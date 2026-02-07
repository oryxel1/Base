plugins {
    id("java")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(libs.gdx.base)

    implementation(libs.imgui.binding)
    implementation(libs.imgui.lwjgl3)

    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.nfd)

    implementation(variantOf(libs.raphimc.thingl) { classifier("java17") })
    implementation(libs.raphimc.audiomixer)

    implementation(libs.jcraft.jorbis)
    implementation(libs.javazoom.jlayer)

//    implementation(libs.jthink.jaudiotagger)
}