plugins {
    id("java")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(libs.gdx.base)

    implementation(libs.imgui.binding)
    implementation(libs.imgui.lwjgl3)
}