package dmalohlovets.money24.framework.web

import dmalohlovets.framework.web.WebTestConfig
import org.junit.jupiter.api.AfterAll
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import javax.annotation.PostConstruct

@SpringBootTest(classes = [WebTestConfig::class])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
open class WebBaseTest {
    @PostConstruct
    private fun init() {
        driverStatic = driver
    }

    @Autowired
    protected lateinit var driver: WebDriver

    @Value("\${app.url}")
    protected lateinit var url: String

    @Value("\${app.aws.db}")
    protected lateinit var aws_db: String

    @Value("\${app.aws.region}")
    protected lateinit var aws_region: String

    @Value("#{'\${app.mobile}'.replace(' ', '').trim()}")
    protected lateinit var app_mobile: String

    companion object {
        @JvmStatic
        protected var isCI = System.getenv()["CI"].toBoolean()

        @JvmStatic
        protected var driverStatic: WebDriver? = null

        @JvmStatic
        @AfterAll
        fun finish() {
            driverStatic?.quit()
        }
    }
}
