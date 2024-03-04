package com.dmalohlovets.tests.unex.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
@Lazy
class UnexMainPage : BaseWebPage() {
    @FindBy(css = ".tabs__nav > span:nth-of-type(2)")
    lateinit var onlineRatesBtn: WebElement

    @FindBy(css = ".departments  > .choices")
    lateinit var cityChoose: WebElement

    @FindBy(css = "[name='search_terms']")
    lateinit var search: WebElement

    @FindBy(xpath = "//div[contains(@class,'is-active')]//div[@data-currency='USD']//div[@data-currency-sale]")
    lateinit var maxValue: WebElement

    @FindBy(xpath = "//div[contains(@class,'is-active')]//div[@data-currency='USD']//div[@data-currnecy-buy]")
    lateinit var minValue: WebElement
}
