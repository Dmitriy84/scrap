package dmalohlovets.money24

import dmalohlovets.framework.web.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.stereotype.Component

@Component
class MainPage : BaseWebPage() {
    @FindBy(className = "bay")
    lateinit var min: MutableList<WebElement>

    @FindBy(className = "sale")
    lateinit var max: MutableList<WebElement>

    @FindBy(xpath = "//a[.='Обміняти']")
    lateinit var flag: MutableList<WebElement>
}
