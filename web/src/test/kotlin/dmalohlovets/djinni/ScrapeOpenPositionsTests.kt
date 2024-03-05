package dmalohlovets.djinni

import dmalohlovets.framework.web.WebBaseTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.io.FileOutputStream
import java.io.OutputStream

private const val OUTPUT_FILE = "jobs.csv"

@EnableConfigurationProperties(DjinniConfig::class)
class ScrapeOpenPositionsTests : WebBaseTest() {
    //    private var flag: Boolean = false

//    @TestFactory
// //    @ResourceLock("flag")
//    @Execution(ExecutionMode.CONCURRENT)
//    fun dynamicNodeSingleContainer(): DynamicNode? {
//        var flag = false
//        return dynamicContainer("palindromes",
//            (1..maxPages.toInt())
//                .map { text ->
//                    if (flag)return null
//                    println("Flag: $flag")
//                    dynamicTest(text.toString()) {
// //                        assertTrue(isPalindrome(text))
//                        if (text == 100) flag = true
//                        System.err.println(Thread.currentThread().name)
//                    }
//                })
//    }

    @Test
    fun scrap() {
        driver["$url/jobs"]
        val result = mutableListOf<Array<String>>()

        (2..maxPages.toInt()).takeWhile { c ->
            jobsPage.rows
                .takeWhile { r ->
                    r.findElement(By.xpath("div[1]")).text[0].isLetter()
                }.map { r ->
                    arrayOf(
                        "\"${r.findElement(By.xpath("div[2]/a/span")).text}\"",
                        r.findElement(By.xpath("div[1]")).text.split(" ")[0],
                        r.findElement(By.xpath("div[2]/a")).getAttribute("href"),
                    )
                }.let {
                    result.addAll(it)
                    println("Total collected elements: ${result.size} ... opening $url/jobs/?page=$c")
                    driver["$url/jobs/?page=$c"]
                    it.size == 15
                }
        }

        FileOutputStream(OUTPUT_FILE).writeCsv(
            listOf(arrayOf("title", "created", "link")) +
                result.filterNot { arr -> filtered.any { word -> arr[0].lowercase().contains(word.trim()) } },
        )
    }

    @Autowired
    private lateinit var jobsPage: JobsPage

    @Value("#{'\${app.filtered}'.toLowerCase().split('\n')}")
    private lateinit var filtered: List<String>

    @Value("\${app.max_pages}")
    private lateinit var maxPages: Number

    companion object {
        @JvmStatic
        @AfterAll
        internal fun finish() {
            Runtime.getRuntime().exec("open $OUTPUT_FILE")
        }
    }
}

fun OutputStream.writeCsv(data: List<Array<String>>) =
    bufferedWriter().use {
        data.map { arr -> arr.joinToString(",", postfix = "\n") }
            .forEach(it::write)
        it.flush()
    }
