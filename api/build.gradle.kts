plugins {
    id("java")
}

dependencies {
    annotationProcessor(libs.projectlombok.lombok)
    implementation(libs.projectlombok.lombok)
    implementation(libs.google.gson)
}