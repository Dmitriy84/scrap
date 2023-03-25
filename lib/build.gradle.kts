@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin)
    `java-library`
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    with(libs) {
        arrayOf(
            kotlin.lib,
            bundles.kotest,
            bundles.spring.boot.test,
            bundles.restassured,
        ).forEach { api(it) }
    }
}
