package com.dmalohlovets.tests.framework.web.config

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.dmalohlovets.tests.framework.web.annotations.WebdriverScopeBean
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Scope
import java.time.Duration

@ComponentScan("com.dmalohlovets.tests")
@EnableDynamoDBRepositories(basePackages = ["com.dmalohlovets.tests.framework.web"])
open class WebTestConfig {
    @WebdriverScopeBean
    open fun createWebdriver(
        @Value("\${wdm.defaultBrowser:chrome}") browser: String,
        @Value("#{'\${wdm.chromeDriver.capabilities:--disable-gpu}'.toLowerCase().split('\n')}") options: MutableList<String>
    ) =
        with(WebDriverManager.getInstance(browser)) {
            capabilities(ChromeOptions().addArguments(options))
            create()
        }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun webdriverWait(driver: WebDriver?) =
        WebDriverWait(driver, Duration.ofSeconds(5))

    @Bean
    fun amazonDynamoDB(
        @Value("\${amazon.dynamodb.endpoint}") endpoint: String,
        @Value("\${amazon.aws.accesskey}") accessKey: String,
        @Value("\${amazon.aws.secretkey}") secretKey: String
    ) = AmazonDynamoDBClient(BasicAWSCredentials(accessKey, secretKey))
        .apply {
            setEndpoint(endpoint)
        }

    @Primary
    @Bean
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB): DynamoDBMapper {
        return DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT)
    }
}
