package dmalohlovets.money24

import dmalohlovets.framework.web.WebBaseTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private const val OUTPUT_FILE = "rates.csv"

@EnableConfigurationProperties(ProjectConfig::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ScrapeRatesTests : WebBaseTest() {
    @Test
    fun scrap() = runTest(dispatchTimeoutMs = 13.hours.inWholeMilliseconds) {
        if (!Files.exists(Path.of(OUTPUT_FILE)))
            FileOutputStream(OUTPUT_FILE, true).writeCsv("min", "max", "date")

        repeat(if (isCI) 1 else 24) {
            if (!isCI)
                driver.manage().window().minimize()
            driver[url]

            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            val min = mainPage.min[0].text.split("\n")[1]
            val max = mainPage.max[0].text.split("\n")[1]

            async(Dispatchers.IO) {
                FileOutputStream(OUTPUT_FILE, true).writeCsv(min, max, date)
            }

            async {
                withContext(Dispatchers.Default) {
                    delay(30.minutes)
                    driver.navigate().refresh()
                }
            }.await()
        }
    }

    @Autowired
    private lateinit var mainPage: MainPage

    private fun OutputStream.writeCsv(data: List<Array<String>>) = bufferedWriter().use {
        data.map { arr -> arr.joinToString(",", postfix = "\n") }
            .forEach(it::write)
        it.flush()
    }

    private fun OutputStream.writeCsv(vararg data: String) = bufferedWriter().use {
        it.write(data.joinToString(",", postfix = "\n"))
        it.flush()
    }

    companion object {
        @JvmStatic
        @AfterAll
        internal fun finish() {
            if (!isCI)
                Runtime.getRuntime().exec(arrayOf("open", OUTPUT_FILE))
        }
    }
}