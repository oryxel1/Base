plugins {
    id("java")
    id("java-library")
}

group = "oxy.bascenario"
version = "1.0"

dependencies {
    api(project(":api"))
    api(libs.commons.core)

    testImplementation(project(":api"))
    testImplementation(libs.gdx.base)
}