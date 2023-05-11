package io.tzero.aqa.framework.matchers

import net.javacrumbs.jsonunit.core.ParametrizedMatcher
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

internal class DoubleMatcher : BaseMatcher<Any?>(), ParametrizedMatcher {
    override fun matches(item: Any) =
        item.toString().toDouble() == param?.toDouble()

    override fun describeTo(description: Description) {
        description.appendValue(param)
    }

    override fun describeMismatch(item: Any?, description: Description) {
        description.appendText("It is not double or equals: ").appendValue(param)
    }

    override fun setParameter(parameter: String) {
        param = parameter
    }

    private var param: String? = null
}
