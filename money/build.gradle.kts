dependencies{
    testImplementation(libs.webdriver.manager)
    testImplementation(libs.selenium)
    testImplementation(project(mapOf("path" to ":web")))
    testImplementation(libs.dynamodb)
    testImplementation(platform("software.amazon.awssdk:bom:2.21.27"))
    testImplementation("software.amazon.awssdk:dynamodb-enhanced")
}
