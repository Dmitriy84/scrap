package com.dmalohlovets.tests.izi.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
@Lazy
class IziMainPage : BaseWebPage() {
    @FindBy(xpath = "//div[contains(@class,'currency-card')]/div[span[contains(text(),'usd')]][1]")
    lateinit var allRates: WebElement
}
