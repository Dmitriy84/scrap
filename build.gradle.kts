plugins {
    id("aqa.framework")
    alias(libs.plugins.kotlin.serialization)
}

group = "com.dmalohlovets.tests"
version = "1.2"

allprojects {
    dependencies {
        testImplementation(rootProject.projects.aqaFramework)
    }
}
