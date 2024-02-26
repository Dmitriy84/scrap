plugins {
    id("aqa.framework")
}

group = "com.dmalohlovets.tests"
version = "1.3"

allprojects {
    dependencies {
        testImplementation(rootProject.projects.aqaFramework)
    }

    tasks {
        allureAggregateReport.get().mustRunAfter(":mono:copyAllureResults")
    }
}
