plugins {
    id("java")
}

group = "oxy.bascenario"
version = "1.0"

var lwjgl = "3.3.6"
var lwjglStd = "3.4.0-SNAPSHOT"
var libGdx = "1.13.1"

repositories {
    mavenCentral()
    maven("https://maven.lenni0451.net/snapshots")
    maven("https://central.sonatype.com/repository/maven-snapshots")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    annotationProcessor(libs.projectlombok.lombok)
    implementation(libs.projectlombok.lombok)

    implementation(libs.raphimc.thingl)

    implementation(libs.google.gson)

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