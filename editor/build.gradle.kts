plugins {
    id("java")
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
}