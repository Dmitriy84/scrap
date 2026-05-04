dependencies {
    arrayOf(
        libs.webdriver.manager,
        libs.selenium,
//        libs.dynamodb,
        libs.aws.dynamodb.enhanced,
        libs.aws.dynamodb,
        libs.sns,
        libs.playwright,
        project(mapOf("path" to ":web")),
        project(mapOf("path" to ":mono")),
        "org.junit-pioneer:junit-pioneer:2.2.0",
    ).forEach { testImplementation(it) }
}

tasks {
    register<Copy>("copyAllureResults") {
        group = "tools"
        description = "Copies existing allure results to include to be included in the new report"
        from("../mono/build/allure-results") {
            include("**/*.json")
            include("**/*.txt")
            exclude("executor.json")
        }
        into("build/allure-results")
        mustRunAfter(":mono:test")
    }

    test {
        finalizedBy("copyAllureResults")
    }
}
