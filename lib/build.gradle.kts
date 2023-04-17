import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
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
            totp,
            json.assert,
            kotlin.serialization.json,
        ).forEach { api(it) }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        }
    }
}
