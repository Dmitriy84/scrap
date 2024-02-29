package com.dmalohlovets.tasks.richtechio.pages

import com.dmalohlovets.tasks.framework.base.BasePlaywrightWebPage
import org.springframework.stereotype.Component


@Component
open class Simple7CharValidationPage : BasePlaywrightWebPage() {
    override var pageURL = "styled/apps/7charval/simple7charvalidation.html"

    val characters: String = "input[name=characters]"
    val message: String = "input[name=validation_message]"
    val validate: String = "input[name=validate]"
}
