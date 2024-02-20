package com.dmalohlovets.tests.web.base

import com.dmalohlovets.tests.web.config.ProjectConfig
import org.awaitility.Awaitility
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.AbstractDriverOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.VncRecordingContainer
import org.testcontainers.lifecycle.TestDescription
import java.io.File
import java.util.Objects
import java.util.Optional
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct


@SpringBootTest(classes = [ProjectConfig::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
open class BaseTestContainerWebTest {
    @Value("\${app.url}")
    protected lateinit var url: String

    @Value("\${app.containers.selenium.start-timeout-seconds:30}")
    private lateinit var timeout: Integer

    @Value("\${app.containers.selenium.save-video-folder:build}")
    private lateinit var saveFolded: String

    @Value("\${app.containers.selenium.save-video:false}")
    private lateinit var isRecord: java.lang.Boolean

    private lateinit var testInfo: TestInfo

    @BeforeEach
    fun setTestInfo(info: TestInfo) {
        testInfo = info
    }

    @AfterEach
    fun saveVideo() {
        staticSeleniumContainer.afterTest(object : TestDescription {
            override fun getTestId() = ""

            override fun getFilesystemFriendlyName() =
                testInfo.testMethod.get().name
        }, Optional.empty())
    }

    @Autowired
    private lateinit var capabilities: AbstractDriverOptions<out AbstractDriverOptions<*>>

    @PostConstruct
    private fun finalizeBeanConstruction() {
        staticCapabilities = capabilities
        staticTimeout = timeout.toLong()
        staticSaveFolder = saveFolded
        staticIsRecord = isRecord.booleanValue()
    }

    companion object {
        @JvmStatic
        private var staticCapabilities: AbstractDriverOptions<out AbstractDriverOptions<*>>? = null

        @JvmStatic
        private var staticTimeout: Long = 0

        @JvmStatic
        private var staticSaveFolder = ""

        @JvmStatic
        private var staticIsRecord: Boolean = false

        @JvmStatic
        var staticDriver: WebDriver? = null

        @JvmStatic
        protected val staticSeleniumContainer: BrowserWebDriverContainer<*> by lazy {
            BrowserWebDriverContainer()
                .withCapabilities(staticCapabilities)
                .withReuse(true)
                .also {
                    if (staticIsRecord)
                        it.withRecordingMode(
                            BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
                            File(staticSaveFolder),
                            VncRecordingContainer.VncRecordingFormat.MP4
                        )
                }
        }

        @JvmStatic
        @BeforeAll
        fun initDriver() {
            staticSeleniumContainer.start()
            staticDriver =
                Awaitility.await().atMost(staticTimeout, TimeUnit.SECONDS).until(
                    {
                        RemoteWebDriver(
                            staticSeleniumContainer.seleniumAddress,
                            staticCapabilities
                        )
                    },
                    Objects::nonNull
                )
        }
    }
}
