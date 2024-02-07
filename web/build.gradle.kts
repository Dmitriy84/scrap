version = "1.2"

dependencies {
    arrayOf(
        libs.webdriver.manager,
        libs.selenium,
    ).forEach { testImplementation(it) }
}
