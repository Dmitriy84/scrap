plugins {
    id("aqa.framework")
}

group = "com.dmalohlovets.tests"
version = "1.3"

allprojects {
    dependencies {
        testImplementation(rootProject.projects.aqaFramework)
        testImplementation(rootProject.libs.dynamodb.spring)
    }
}
