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