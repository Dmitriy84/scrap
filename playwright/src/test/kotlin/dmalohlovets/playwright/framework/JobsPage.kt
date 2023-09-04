package dmalohlovets.playwright.framework

import org.springframework.stereotype.Component

@Component
class JobsPage {
     val rows = "//li[@class='list-jobs__item list__item']/div[1]"
}
