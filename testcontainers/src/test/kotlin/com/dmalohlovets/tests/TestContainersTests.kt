package com.dmalohlovets.tests

import com.dmalohlovets.tests.web.base.BaseTestContainerWebTest
import com.dmalohlovets.tests.web.config.ProjectConfig
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties(ProjectConfig::class)
class TestContainersTests : BaseTestContainerWebTest() {
    @Test
    @Tag("testcontainer")
    fun selenium() {
        staticDriver?.get(url)
    }
}
