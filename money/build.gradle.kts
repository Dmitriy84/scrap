dependencies {
    with(libs) {
        arrayOf(webdriver.manager, selenium, dynamodb, sns)
            .forEach { testImplementation(it) }
    }
    testImplementation(project(mapOf("path" to ":web")))
}
