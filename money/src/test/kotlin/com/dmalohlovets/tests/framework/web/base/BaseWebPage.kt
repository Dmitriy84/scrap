package com.dmalohlovets.tests.framework.web.base

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
open class BaseWebPage {
    @Autowired
    protected lateinit var driver: WebDriver

    @PostConstruct
    private fun init() = PageFactory.initElements(driver, this)
}
