package com.dmalohlovets.tests.framework.web.config

import com.dmalohlovets.tests.framework.web.annotations.WebdriverScopeBean
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.ComponentScan


@ComponentScan("com.dmalohlovets.tests")
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
}
