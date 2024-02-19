dependencies {
    arrayOf(
        libs.dynamodb,
    ).forEach { testImplementation(it) }
}
