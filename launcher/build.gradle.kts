plugins {
    id("java")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(libs.gdx.base)
    implementation(libs.gdx.spine)

    implementation(libs.imgui.binding)
    implementation(libs.imgui.lwjgl3)

    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.nfd)

    implementation(libs.raphimc.thingl)
    implementation(libs.raphimc.audiomixer)

    implementation(libs.commons.animations)
}