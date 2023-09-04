package aqa.framework.utils

import com.savvasdalkitsis.jsonmerger.JsonMerger
import aqa.framework.api.base.BaseTest
import aqa.framework.api.support.KotlinxGenericMapSerializer
import aqa.framework.matchers.DoubleMatcher
import net.javacrumbs.jsonunit.assertj.JsonAssertions
import org.json.JSONObject

object JsonUtils {
    fun String.toBe(expected: String, vararg ignore: String) {
        JsonAssertions.assertThatJson(this)
            .withMatcher("Double", DoubleMatcher())
            .whenIgnoringPaths(*ignore)
            .isEqualTo(expected)
    }

    fun String.merge(override: String) = JsonMerger(objectMergeMode = JsonMerger.ObjectMergeMode.MERGE_OBJECT)
        .merge(this, override)

    fun String.merge(override: JSONObject) = merge(override.toString())

    fun String.merge(override: Map<String, Any>) =
        merge(BaseTest.json.encodeToString(KotlinxGenericMapSerializer, override))
}
