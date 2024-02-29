package com.dmalohlovets.tasks.richtechio.config

import aqa.framework.api.config.YamlPropertySource
import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource


@Configuration
@ConfigurationProperties
@ComponentScan(basePackages = ["com.dmalohlovets.tasks"])
@PropertySource("application-\${spring.profiles.active}.yml", factory = YamlPropertySource::class)
open class ProjectConfig {
    @Bean
    open fun createBrowser(
        @Value("\${app.web.browser:chromium}") browser: String,
        @Value("\${app.web.headless:true}") headless: String,
        @Value("#{'\${app.web.capabilities}'.toLowerCase().split('\n')}") capabilities: MutableList<String>,
    ): Browser = when (browser) {
        "chromium" -> Playwright.create().chromium()
        "firefox" -> Playwright.create().firefox()
        else -> throw IllegalArgumentException("unsupported browser: $browser")
    }.launch(
        BrowserType.LaunchOptions().setHeadless(headless.toBoolean()).setArgs(capabilities)
    )
}
