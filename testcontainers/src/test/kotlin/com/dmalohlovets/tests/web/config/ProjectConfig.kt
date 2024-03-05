package com.dmalohlovets.tests.web.config

import aqa.framework.api.config.YamlPropertySource
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.AbstractDriverOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties
@ComponentScan
@PropertySource("application-\${spring.profiles.active}.yml", factory = YamlPropertySource::class)
open class ProjectConfig {
    @Value("\${app.browser.name:chrome}")
    private lateinit var browser: String

    @Value("#{'\${app.browser.capabilities}'.toLowerCase().split('\n')}")
    private lateinit var capabilitiesConfiguration: List<String>

    @get:Bean
    open val capabilities: AbstractDriverOptions<out AbstractDriverOptions<*>>?
        get() =
            when (browser) {
                "chrome" -> ChromeOptions().addArguments(capabilitiesConfiguration)
                "firefox" -> FirefoxOptions().addArguments(capabilitiesConfiguration)
                "edge" -> EdgeOptions().addArguments(capabilitiesConfiguration)
                else -> throw Exception("Unsupported browser: '$browser'")
            }
}
