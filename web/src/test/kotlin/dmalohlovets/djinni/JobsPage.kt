package dmalohlovets.djinni

import dmalohlovets.framework.web.BaseWebPage
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.springframework.stereotype.Component

@Component
class JobsPage : BaseWebPage() {
    @FindBy(xpath = "//li[@class='list-jobs__item list__item']/div[1]")
    lateinit var rows: MutableList<WebElement>
}
