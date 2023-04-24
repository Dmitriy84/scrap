package io.tzero.aqa.framework.api.config

import io.restassured.RestAssured
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.Scope

@ComponentScan
@Configuration
@PropertySource("application-\${test.env:staging}.properties")
open class ProjectConfig {
    @Value("\${BASEURL}")
    private lateinit var baseURL: String

    @Value("\${response.timeout.max}")
    private lateinit var timeout: String

    @Bean
    @Scope("singleton")
    open fun getRestAssured(): RestAssured {
        RestAssured.baseURI = baseURL
        RestAssured.filters(RequestLoggingFilter(), ResponseLoggingFilter())
        RestAssured.useRelaxedHTTPSValidation()
        RestAssured.responseSpecification =
            ResponseSpecBuilder().expectResponseTime(Matchers.lessThan(timeout.toLong())).build()
        return RestAssured()
    }
}
