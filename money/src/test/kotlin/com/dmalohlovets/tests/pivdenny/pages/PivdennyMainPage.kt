package com.dmalohlovets.tests.pivdenny.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
@Lazy
class PivdennyMainPage : BaseWebPage() {
    @FindBy(xpath = "//div[@class='converter__body-where']//button[@class='base-dropdown__current']")
    lateinit var currencyTargetBtn: WebElement

    @FindBy(xpath = "//div[@class='base-dropdown__body active']//button[contains(text(),'Mobile banking')]")
    lateinit var currencyMobileBtn: WebElement

    @FindBy(xpath = "//div[span[contains(text(),'USD')]]/following::div[2]/span")
    lateinit var currencyUsdMax: WebElement

    @FindBy(xpath = "//div[span[contains(text(),'USD')]]/following::div[1]/span")
    lateinit var currencyUsdMin: WebElement

    @FindBy(css = "div.popup-city-step1-btn > div > button > span.btn-blue__text.btn-blue__text--static.uikit__link")
    lateinit var cityConfirmationBtn: List<WebElement>
}
