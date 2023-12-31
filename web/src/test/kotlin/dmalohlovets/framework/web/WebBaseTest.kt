package dmalohlovets.framework.web

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

    companion object {
        @JvmStatic
        protected var driverStatic: WebDriver? = null

        @JvmStatic
        @AfterAll
        fun finish() {
            driverStatic?.quit()
        }
    }
}
