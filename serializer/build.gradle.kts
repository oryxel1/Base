plugins {
    id("java")
    id("java-library")
}

dependencies {
    api(project(":api"))
    api(libs.commons.core)

    testImplementation(project(":core"))
    testImplementation(libs.gdx.base)
}