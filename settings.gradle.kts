enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "djinni"
val modules = "libs"
include(
    "web",
    "money",
    "playwright",
)

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("$modules/aqa-framework/gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("libs/aqa-framework/plugin")
}

file(modules)
    .listFiles(File::isDirectory)
    ?.forEach {
        include(it.name)
        //if ${it} subfolder contains 'lib' subfolder, then use it for the projectDir
        (it.listFiles { sub -> "lib" == sub.name }?.firstOrNull() ?: it)
            .also { dir ->
                logger.quiet("Including lib ---> $dir")
                project(":${it.name}").projectDir = dir
            }
    }
