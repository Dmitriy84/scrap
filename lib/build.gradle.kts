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
    arrayOf(
        libs.kotlin.lib,
        libs.bundles.kotest,
        libs.bundles.spring.boot.test,
        libs.bundles.restassured,
    ).forEach { implementation(it) }
}
