package com.dmalohlovets.tests.config

import aqa.framework.api.config.ProjectAPIConfig
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.DirtiesContext


@ComponentScan
@EnableAutoConfiguration
@SpringBootTest(classes = [ProjectAPIConfig::class]) // Rest assured bean configuration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseApiTest
