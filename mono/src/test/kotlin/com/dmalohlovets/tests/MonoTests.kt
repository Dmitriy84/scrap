package com.dmalohlovets.tests

import aqa.framework.utils.SpecUtils.extractJsonValues
import com.dmalohlovets.tests.config.BaseApiTest
import com.dmalohlovets.tests.config.components.RatesDynamoDbInserter
import com.dmalohlovets.tests.config.interfaces.DataInserter.Companion.dateOf
import io.restassured.RestAssured.get
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.Date


class MonoTests : BaseApiTest() {
    @Autowired
    private lateinit var ratesDynamoDbInserter: RatesDynamoDbInserter

    @Test
    @Tag("scrap")
    @Tag("mono")
    fun getRates() = runTest {
        val (date, min, max) = get("/bank/currency")
            .then()
            .extractJsonValues("[0].date", "[0].rateBuy", "[0].rateSell")

        ratesDynamoDbInserter.putItem(
            dateOf(Date(date.toLong() * 1000).toInstant()),
            max,
            min,
            "mono"
        )
    }
}
