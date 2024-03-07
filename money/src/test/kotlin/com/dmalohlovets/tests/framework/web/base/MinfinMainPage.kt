package com.dmalohlovets.tests.framework.web.base

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class MinfinMainPage : BaseWebPage() {
    @FindBy(css = ".ExchangeRates__table2 > tr:nth-of-type(1) > td:nth-of-type(2) > div")
    lateinit var minValue: WebElement

    @FindBy(css = ".ExchangeRates__table2 > tr:nth-of-type(1) > td:nth-of-type(3) > div")
    lateinit var maxValue: WebElement
}
