plugins {
    id("java")
}

group = "oxy.bascenario"
version = "1.0"

dependencies {
    implementation(project(":api"))

    implementation(libs.netty.buffer)
    implementation(libs.commons.core)

    testImplementation(project(":core"))
    testImplementation(libs.gdx.base)
}