package com.dmalohlovets.tests.framework.web.base

import com.dmalohlovets.tests.framework.web.annotations.LazyAutowired
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
open class BaseWebPage {
    @LazyAutowired
    protected lateinit var driver: WebDriver

    @PostConstruct
    private fun init() = PageFactory.initElements(driver, this)
}
