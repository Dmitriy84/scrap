package com.dmalohlovets.tasks.framework.base

import com.dmalohlovets.tasks.richtechio.config.ProjectConfig
import com.microsoft.playwright.Browser
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext


@SpringBootTest(classes = [ProjectConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
open class BasePlaywrightWebTest {
    @Autowired
    protected lateinit var browser: Browser

    @AfterAll
    fun tearDown() {
        browser.close()
    }
}
