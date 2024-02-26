package com.dmalohlovets.tests.sense.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
@Lazy
class SenseMainPage : BaseWebPage() {
    @FindBy(css = "button.home-exchange__tab.text--sm:nth-of-type(2)")
    lateinit var onlineRatesBtn: WebElement

    @FindBy(css = "div.preloader.preloader_hidden")
    lateinit var loading: WebElement

    @FindBy(xpath = "//div[@class='home-exchange__item' and h3/span[contains(text(),'USD')]]/ul")
    lateinit var usdField: WebElement
}
