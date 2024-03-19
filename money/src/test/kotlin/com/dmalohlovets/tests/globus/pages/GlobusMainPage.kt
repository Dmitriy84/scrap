package com.dmalohlovets.tests.globus.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.stereotype.Component

@Component
class GlobusMainPage : BaseWebPage() {
    @FindBy(xpath = "//td[text()='USD']/following::td[1]")
    lateinit var minValue: WebElement

    @FindBy(xpath = "//td[text()='USD']/following::td[2]")
    lateinit var maxValue: WebElement
}
