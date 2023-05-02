package io.tzero.aqa.framework.api.base

import io.kotest.core.spec.style.AnnotationSpec
import io.tzero.aqa.framework.api.config.ProjectConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ProjectConfig::class])
open class BaseTest : AnnotationSpec() {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    @Value("\${test.env:staging}")
    lateinit var env: String

    protected inline fun <reified R> getObjectFromJson(path: String) =
        json.decodeFromStream<R>(javaClass.classLoader.getResourceAsStream("data/$env/$path"))

    protected val json = Json {
        encodeDefaults = true
        isLenient = true
    }
}
