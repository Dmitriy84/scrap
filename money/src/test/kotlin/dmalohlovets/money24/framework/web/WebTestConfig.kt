package dmalohlovets.framework.web

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConfigurationProperties
@ComponentScan("com.dmalohlovets.tests.config.components")
open class WebTestConfig {
    @get:Bean
    open val webDriver
        get() =
            with(WebDriverManager.getInstance(browser)) {
                capabilities(ChromeOptions().addArguments(options))
                create()
            }

    @Value("\${wdm.defaultBrowser}")
    private lateinit var browser: String

    @Value("#{'\${wdm.chromeDriver.capabilities}'.toLowerCase().split('\n')}")
    private lateinit var options: MutableList<String>
}
