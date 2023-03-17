package com.tzero.aqa.framework.api.base

import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import com.tzero.aqa.framework.api.config.ProjectConfig

@SpringBootTest(classes = [ProjectConfig::class])
open class BaseTest : io.kotest.core.spec.style.AnnotationSpec() {
    val log: Logger = LoggerFactory.getLogger(javaClass)
}
