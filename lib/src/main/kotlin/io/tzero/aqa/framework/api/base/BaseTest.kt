package io.tzero.aqa.framework.api.base

import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import io.tzero.aqa.framework.api.config.ProjectConfig
import io.kotest.core.spec.style.AnnotationSpec

@SpringBootTest(classes = [ProjectConfig::class])
open class BaseTest : AnnotationSpec() {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    @Value("\${test.env:staging}")
    lateinit var env: String

    protected inline fun <reified R> getObjectFromJson(path: String) =
        json.decodeFromStream<R>(javaClass.classLoader.getResourceAsStream("data/$env/$path"))
}
