import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
            kotlin.serialization.json,
            bundles.kotest,
            bundles.spring.boot.test,
            bundles.restassured,
            totp,
            json.assert,
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
