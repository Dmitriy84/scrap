package dmalohlovets.playwright.framework

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.io.FileOutputStream
import java.io.OutputStream

@EnableConfigurationProperties(DjinniConfig::class)
class DjinniTest : BaseTests() {
    @Test
    fun scrap() {
        page.navigate("$url/jobs")
        val result = mutableListOf<Array<String>>()

        (2..maxPages.toInt()).takeWhile { c ->
            page.locator(jobsPage.rows).all()
                .takeWhile { r ->
                    r.locator("div:nth-child(1)").textContent().trim()[0].isLetter()
                }.map { r ->
                    arrayOf(
                        "\"${r.locator("div:nth-child(2) > a > span").textContent()}\"",
                        r.locator("div:nth-child(1)").textContent().trim().split(" ")[0].trim(),
                        url + r.locator("div:nth-child(2) > a").getAttribute("href")
                    )
                }.let {
                    result.addAll(it)
                    println("Total collected elements: ${result.size} ... opening $url/jobs/?page=$c")
                    page.navigate("$url/jobs/?page=$c")
                    it.size == 15
                }
        }

        FileOutputStream(OUTPUT_FILE).writeCsv(
            listOf(arrayOf("title", "created", "link"))
                    + result.filterNot { arr -> filtered.any { word -> arr[0].lowercase().contains(word.trim()) } }
        )
    }

    @AfterAll
    fun finish() {
        Runtime.getRuntime().exec("open $OUTPUT_FILE")
    }

    @Autowired
    private lateinit var jobsPage: JobsPage

    @Value("#{'\${app.filtered}'.toLowerCase().split('\n')}")
    private lateinit var filtered: List<String>

    @Value("\${app.max_pages}")
    private lateinit var maxPages: Number

    @Value("\${app.url}")
    private lateinit var url: String

    private val OUTPUT_FILE = "jobs.csv"

    private fun OutputStream.writeCsv(data: List<Array<String>>) = bufferedWriter().use {
        data.map { arr -> arr.joinToString(",", postfix = "\n") }
            .forEach(it::write)
        it.flush()
    }
}