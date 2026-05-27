plugins {
    id("java")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(libs.gdx.base)

    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.nfd)

    implementation(libs.raphimc.thingl)
    implementation(libs.raphimc.audiomixer)

    implementation(libs.jcraft.jorbis)
    implementation(libs.javazoom.jlayer)

    implementation(libs.rivet.core)
    implementation(libs.rivet.thingl.backend)
}