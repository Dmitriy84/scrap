package com.dmalohlovets.tests.money24.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.stereotype.Component

@Component
class Money24MainPage : BaseWebPage() {
    @FindBy(className = "bay")
    lateinit var min: MutableList<WebElement>

    @FindBy(className = "sale")
    lateinit var max: MutableList<WebElement>

    @FindBy(xpath = "//a[.='Обміняти']")
    lateinit var flag: MutableList<WebElement>
}
