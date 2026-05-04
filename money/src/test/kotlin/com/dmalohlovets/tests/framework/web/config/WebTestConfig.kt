package com.dmalohlovets.tests.framework.web.config

import com.dmalohlovets.tests.framework.web.annotations.WebdriverScopeBean
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Scope
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI
import java.time.Duration

@ComponentScan("com.dmalohlovets.tests")
open class WebTestConfig {
    @WebdriverScopeBean
    open fun createWebdriver(
        @Value("\${wdm.defaultBrowser:chrome}") browser: String,
        @Value("#{'\${wdm.chromeDriver.capabilities:--disable-gpu}'.toLowerCase().split('\n')}") options: MutableList<String>,
    ) = with(WebDriverManager.getInstance(browser)) {
        capabilities(ChromeOptions().addArguments(options))
//        create()
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun webdriverWait(driver: WebDriver?) = WebDriverWait(driver, Duration.ofSeconds(5))

    @Bean
    fun dynamoDbClient(
        @Value("\${amazon.dynamodb.endpoint}") endpoint: String,
        @Value("\${amazon.aws.accesskey}") accessKey: String,
        @Value("\${amazon.aws.secretkey}") secretKey: String,
        @Value("\${app.aws.region}") region: String,
    ): DynamoDbClient {
        println("DMYTRO: $endpoint")
        return DynamoDbClient.builder()
            .endpointOverride(URI.create(endpoint)) // для LocalStack
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey),
                ),
            )
            .build()
    }

    @Bean
    fun dynamoDbEnhancedClient(client: DynamoDbClient): DynamoDbEnhancedClient? =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(client)
            .build()
}
