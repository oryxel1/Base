plugins {
    id("java")
}

group = "oxy.bascenario"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.lenni0451.net/snapshots")
        maven("https://central.sonatype.com/repository/maven-snapshots")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }

    group = "oxy.bascenario"
    version = "1.0"
}

subprojects {
    apply(plugin = "java")

    dependencies {
        annotationProcessor(rootProject.libs.projectlombok.lombok)
        implementation(rootProject.libs.projectlombok.lombok)
        implementation(rootProject.libs.google.gson)
        implementation(rootProject.libs.configurate.yaml)
    }
}
//
//dependencies {
//    implementation(project(":editor"))
//    implementation(project(":core"))
//    implementation(project(":api"))
//
//    implementation(libs.gdx.base)
//    implementation(libs.commons.core)
//
//    implementation(libs.imgui.binding)
//}