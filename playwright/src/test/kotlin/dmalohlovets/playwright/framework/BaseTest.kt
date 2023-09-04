package dmalohlovets.playwright.framework

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType.LaunchOptions
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(classes = [DjinniConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
open class BaseTests {
    lateinit var browser: Browser
    open lateinit var page: Page

    @BeforeAll
    fun setUp() {
        //Open a browser (supports Chromium (Chrome, Edge), Firefox, and Webkit (Safari))
        browser = Playwright
            .create()
            .chromium()
            .launch(LaunchOptions().setHeadless(false).setArgs(listOf("--start-minimized")))

        //A single browser tab
        page = browser.newPage()
    }

    @AfterAll
    fun tearDown() {
        browser.close()
    }
}
