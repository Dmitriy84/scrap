package aqa.framework.api.base

import io.kotest.core.spec.style.AnnotationSpec
import io.tzero.aqa.framework.api.config.ProjectConfig
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ProjectConfig::class])
open class BaseTest : AnnotationSpec() {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    @Value("\${spring.profiles.active}")
    lateinit var env: String

    @OptIn(ExperimentalSerializationApi::class)
    protected inline fun <reified R> loadJson(path: String) =
        json.decodeFromStream<R>(javaClass.classLoader.getResourceAsStream("data/$env/$path"))

    protected fun loadJson(path: String) =
        javaClass.classLoader.getResource("data/$env/$path").readText(Charsets.UTF_8)

    @OptIn(ExperimentalSerializationApi::class)
    companion object {
        val json = Json {
            encodeDefaults = true
            isLenient = true
            explicitNulls = false
            ignoreUnknownKeys = true
        }
    }

    protected inline fun <T, R> T.resultOf(block: T.() -> R): Result<R> {
        return try {
            Result.success(block())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
