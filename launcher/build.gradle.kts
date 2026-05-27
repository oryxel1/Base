plugins {
    id("java")
    id("com.gradleup.shadow") version("9.4.1")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":editor"))
    implementation(project(":serializer"))
    implementation(project(":core"))

    implementation(libs.gdx.base)
    implementation(libs.gdx.spine)

    implementation(libs.lwjgl.glfw)
    implementation(libs.lwjgl.nfd)

    implementation(libs.raphimc.thingl)
    implementation(libs.raphimc.audiomixer)

    implementation(libs.commons.animations)

    implementation(libs.zt.zip)
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