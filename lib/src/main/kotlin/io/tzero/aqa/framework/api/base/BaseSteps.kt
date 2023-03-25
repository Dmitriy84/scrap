package io.tzero.aqa.framework.api.base

import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory

open class BaseSteps {
    val log: Logger = LoggerFactory.getLogger(javaClass)
}
