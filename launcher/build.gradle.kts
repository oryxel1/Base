plugins {
    id("java")
    id("com.gradleup.shadow") version("8.3.0")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":editor"))
    implementation(project(":serializer"))
    implementation(project(":core"))

    implementation(libs.gdx.base)
    implementation(libs.gdx.spine)

    implementation(libs.imgui.binding)
    implementation(libs.imgui.lwjgl3)

    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.nfd)

    implementation(variantOf(libs.raphimc.thingl) { classifier("java17") })
    implementation(libs.raphimc.audiomixer)

    implementation(libs.commons.animations)
}

tasks {
    shadowJar {
        archiveFileName = "Base.jar"
        manifest {
            attributes(
                "Main-Class" to "oxy.bascenario.Main"
            )
        }
    }
}