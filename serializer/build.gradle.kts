plugins {
    id("java")
    id("java-library")
}

dependencies {
    api(project(":api"))
    api(libs.commons.core)

    testImplementation(project(":api"))
    testImplementation(libs.gdx.base)
}