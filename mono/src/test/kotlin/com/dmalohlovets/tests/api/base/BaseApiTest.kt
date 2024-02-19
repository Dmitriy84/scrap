package com.dmalohlovets.tests.api.base

import aqa.framework.api.config.ProjectAPIConfig
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(classes = [ProjectAPIConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
open class BaseApiTest {
    @Value("\${app.aws.db}")
    protected lateinit var aws_db: String

    @Value("\${app.aws.region}")
    protected lateinit var aws_region: String
}
