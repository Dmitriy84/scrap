package io.tzero.aqa.framework.api.config

import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*

@ComponentScan
@Configuration
@PropertySource("application-\${test.env:staging}.properties")
open class ProjectConfig {
    @Value("\${BASEURL}")
    private lateinit var baseURL: String

    @Bean
    @Scope("singleton")
    open fun getRestAssured(): RestAssured {
        RestAssured.baseURI = baseURL
        RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter())
        RestAssured.useRelaxedHTTPSValidation()
        return RestAssured()
    }
}
