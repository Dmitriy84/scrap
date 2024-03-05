package com.dmalohlovets.tasks.framework.base

import com.microsoft.playwright.Browser
import com.microsoft.playwright.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
abstract class BasePlaywrightWebPage {
    abstract var pageURL: String

    @Autowired
    protected lateinit var browser: Browser

    @Value("\${app.web.url}")
    protected lateinit var baseURL: String

    fun newPage() {
        page = browser.newPage()
    }

    lateinit var page: Page

    fun navigate() = page.navigate("$baseURL/$pageURL")

    // TODO Screenshots and videos + allure reporting
}
