plugins {
    id("java")
}

repositories {
    maven("https://jitpack.io")
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

    implementation("com.github.Lenni0451.rivet:core:40c6f6c3a7")
    implementation("com.github.Lenni0451.rivet:backend-thingl-glfw:40c6f6c3a7")
}