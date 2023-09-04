package aqa.framework.api.base

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification
import io.tzero.aqa.framework.api.specs.ResponseSpec
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory

abstract class BaseSteps {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    open lateinit var basePath: String

    fun act(
        requestSpec: RequestSpecification? = null,
        responseSpec: ResponseSpecification = ResponseSpec.`200`,
        action: RequestSpecification.() -> Response
    ) =
        Given {
            requestSpec?.also { spec(it) }
            basePath(basePath)
        } When {
            action()
        } Then {
            spec(responseSpec)
        }
}
