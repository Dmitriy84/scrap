dependencies{
    testImplementation(libs.webdriver.manager)
    testImplementation(libs.selenium)
    testImplementation(project(mapOf("path" to ":web")))
}
