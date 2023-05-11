import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDateTime

val catalog = rootProject.extensions
    .getByType(VersionCatalogsExtension::class.java)
    .named("libs")
val jvm = catalog.findVersion("jvm").orElseThrow().displayName

plugins {
    kotlin("jvm")
}

allprojects {
    arrayOf(
        catalog.findPlugin("kotlin").orElseThrow().get().pluginId,
        catalog.findPlugin("kotlin-serialization").orElseThrow().get().pluginId,
        "java-library",
    ).forEach { apply(plugin = it) }

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri(providers.gradleProperty("ARTIFACTORY_REPO_URI").get())
            credentials {
                username = providers.gradleProperty("ARTIFACTORY_REPO_USER").orNull
                password = providers.gradleProperty("ARTIFACTORY_REPO_PASS").orNull
            }
        }
    }

    dependencies {
        with(catalog) {
            arrayOf(
                kotlin("test"),
                findLibrary("kotlin-lib").orElseThrow(),
                findLibrary("kotlin-serialization-json").orElseThrow(),

                findLibrary("totp").orElseThrow(),
                findLibrary("json-assert").orElseThrow(),
                findLibrary("json-merge").orElseThrow(),

                findBundle("kotest").orElseThrow(),
                findBundle("junit5").orElseThrow(),
                findBundle("spring-boot-test").orElseThrow(),
                findBundle("qase").orElseThrow(),
                findBundle("restassured").orElseThrow(),
                findBundle("json-unit").orElseThrow(),
                findBundle("json-dsl").orElseThrow(),
            ).forEach { api(it) }
        }
    }

    tasks {
        register<Exec>("gitSubmodulesUpdate") {
            group = "tools"
            description = "Update all git submodules from remote repo"
            commandLine("git submodule update --init --recursive --force --remote".split(" "))
        }

        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
                jvmTarget = jvm
            }
        }

        withType<JavaCompile> {
            javaCompiler.set(
                project.javaToolchains.compilerFor {
                    languageVersion.set(JavaLanguageVersion.of(jvm))
                }
            )
        }

        test {
            doFirst {
                infix fun String.setProperty(key: String) {
                    systemProperty(key, this)
                    logger.quiet("Set property ---> $key='$this'")
                }

                "QASE_RUN_NAME".also {
                    if (!providers.systemProperty(it).isPresent || !providers.gradleProperty(it).isPresent) {
                        "Autotest run: ${LocalDateTime.now()}" setProperty it
                        "Test run automatically generated by [${providers.environmentVariable("USER").orNull}] https://gitlab.com/tzero-git/primary-issuance-be" setProperty "QASE_RUN_DESCRIPTION"
                    }
                }

                arrayOf(
                    "QASE_API_TOKEN",
                    "QASE_ENABLE",
                    "QASE_PROJECT_CODE",
                    "QASE_RUN_ID",
                    "QASE_RUN_NAME",
                    "QASE_RUN_AUTOCOMPLETE",
                    "QASE_RUN_DESCRIPTION",
                    "spring.profiles.active",
                ).forEach { key ->
                    (providers.systemProperty(key).orNull
                        ?: providers.gradleProperty(key).orNull)
                        ?.also { value -> value setProperty key }
                }
            }

            useJUnitPlatform()
            testLogging {
                events(PASSED, SKIPPED, FAILED)
                exceptionFormat = TestExceptionFormat.FULL
                showCauses = true
                showExceptions = true
                showStackTraces = true
            }
        }
    }
}
