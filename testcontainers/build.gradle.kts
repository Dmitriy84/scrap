dependencies {
    arrayOf(
        libs.testcontainers,
    )
        .forEach { testImplementation(it) }
}
