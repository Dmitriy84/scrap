package com.dmalohlovets.tests.framework.web.base

import com.dmalohlovets.tests.framework.web.annotations.LazyAutowired
import com.dmalohlovets.tests.framework.web.config.WebTestConfig
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest(classes = [WebTestConfig::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
open class WebBaseTest {
    @LazyAutowired
    protected lateinit var driver: WebDriver

    @Value("#{{\${app.banks}}}")
    protected lateinit var banks: Map<String, String>

    @Value("\${app.aws.db}")
    protected lateinit var awsDb: String

    @Value("\${app.aws.region}")
    protected lateinit var awsRegion: String

    @Value("#{'\${app.mobile}'.replace(' ', '').trim()}")
    protected lateinit var appMobile: String

    companion object {
        @JvmStatic
        protected var isCI = System.getenv()["CI"].toBoolean()
    }
}
