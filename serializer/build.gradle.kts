plugins {
    id("java")
}

group = "oxy.bascenario"
version = "1.0"

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
}