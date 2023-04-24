package io.tzero.aqa.framework.api.config

import io.restassured.RestAssured
import io.restassured.config.HttpClientConfig
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*


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

        val requestConfig = RequestConfig.custom()
            .setConnectTimeout(timeout.toInt())
            .setConnectionRequestTimeout(timeout.toInt())
            .setSocketTimeout(timeout.toInt())
            .build()
        val httpClientFactory = HttpClientConfig.httpClientConfig()
            .httpClientFactory {
                HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig)
                    .build()
            }
        RestAssured.config = RestAssured
            .config()
            .httpClient(httpClientFactory)

        RestAssured.useRelaxedHTTPSValidation()
        return RestAssured()
    }
}
