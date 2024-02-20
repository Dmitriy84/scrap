package com.dmalohlovets.tests.sense.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.stereotype.Component


@Component
class SenseMainPage : BaseWebPage() {
    @FindBy(css = "button.home-exchange__tab.text--sm:nth-of-type(2)")
    lateinit var onlineRatesBtn: WebElement

    @FindBy(xpath = "//div[@class='home-exchange__item' and h3/span[contains(text(),'USD')]]/ul")
    lateinit var usdField: WebElement
}
