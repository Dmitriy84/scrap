plugins {
    id("aqa.framework")
    alias(libs.plugins.kotlin.serialization)
}

version = "1.2"

allprojects {
    dependencies {
        testImplementation(rootProject.projects.aqaFramework)
    }
}
