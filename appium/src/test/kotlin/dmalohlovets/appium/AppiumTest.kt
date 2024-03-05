package dmalohlovets.appium

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import org.junit.jupiter.api.Test
import java.net.URL
import java.nio.file.Path

class AppiumTest {
    @Test
    fun first() {
        val service = AppiumDriverLocalService.buildDefaultService()
        service.start()
        try {
            val options =
                UiAutomator2Options()
                    .setUdid(System.getenv("SIMULATOR_UUID"))
                    .setApp(
                        Path.of(System.getProperty("user.dir"), System.getenv("APP_APK")).toString(),
                    )
            val driver =
                AndroidDriver(
                    URL(System.getenv("SIMULATOR_URL")),
                    options,
                )
            println()
            try {
                val el = driver.findElement(AppiumBy.xpath("//Button"))
                el.click()
                driver.pageSource
            } finally {
                driver.quit()
            }
        } finally {
            service.stop()
        }
    }
}
