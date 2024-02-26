package com.dmalohlovets.tests.framework.web.base

import com.dmalohlovets.tests.framework.web.annotations.LazyAutowired
import com.dmalohlovets.tests.framework.web.config.WebTestConfig
import io.qameta.allure.Allure
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.RegisterExtension
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest(classes = [WebTestConfig::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
open class WebBaseTest {
    @LazyAutowired
    protected lateinit var driver: WebDriver

    @LazyAutowired
    protected lateinit var wait: WebDriverWait

    @Autowired
    private lateinit var ctx: ApplicationContext

    @Value("#{{\${app.banks}}}")
    protected lateinit var banks: Map<String, String>

    @Value("\${app.aws.db}")
    protected lateinit var awsDb: String

    @Value("\${app.aws.region}")
    protected lateinit var awsRegion: String

    @Value("#{'\${app.mobile}'.replace(' ', '').trim()}")
    protected lateinit var appMobile: String

    @RegisterExtension
    var afterTestExecutionCallback: AfterTestExecutionCallback =
        AfterTestExecutionCallback {
            Allure.addAttachment(
                if (it.executionException.isPresent) "Failure screenshot" else "End of test screenshot",
                FileUtils.openInputStream(ctx.getBean(TakesScreenshot::class.java).getScreenshotAs(OutputType.FILE))
            )
        }

    companion object {
        @JvmStatic
        protected var isCI = System.getenv()["CI"].toBoolean()
    }
}
