dependencies {
    arrayOf(
        libs.bundles.testcontainers,
        libs.bundles.selenium,
        libs.awaitility,
    )
        .forEach { testImplementation(it) }
}
