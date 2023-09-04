package dmalohlovets.playwright.framework

import io.tzero.aqa.framework.api.config.YamlPropertySource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties
@ComponentScan
@PropertySource("application-\${spring.profiles.active}.yml", factory = YamlPropertySource::class)
open class DjinniConfig
