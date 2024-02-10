dependencies {
    arrayOf(
        libs.bundles.testcontainers,
        libs.bundles.selenium,
    )
        .forEach { testImplementation(it) }
}
