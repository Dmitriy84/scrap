plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

dependencies {
    implementation(libs.kotlin.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
