package com.dmalohlovets.tests.kredo.pages

import com.dmalohlovets.tests.framework.web.base.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
@Lazy
class KredoMainPage : BaseWebPage() {
    @FindBy(xpath = "//div[@data-type='cards']//tr[th[contains(text(),'USD')]]/td[1]")
    lateinit var maxValue: WebElement

    @FindBy(xpath = "//div[@data-type='cards']//tr[th[contains(text(),'USD')]]/td[2]")
    lateinit var minValue: WebElement
}
