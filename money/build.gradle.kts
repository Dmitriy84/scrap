dependencies {
    arrayOf(
        libs.webdriver.manager,
        libs.selenium,
        libs.dynamodb,
        libs.sns,
        project(mapOf("path" to ":web")),
        project(mapOf("path" to ":mono")),
    ).forEach { testImplementation(it) }
}
