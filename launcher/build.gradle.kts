plugins {
    id("java")
    id("com.gradleup.shadow") version("9.4.1")
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":editor"))
    implementation(project(":serializer"))
    implementation(project(":core"))
    
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

    artifacts {
        archives(shadowJar)
    }
}