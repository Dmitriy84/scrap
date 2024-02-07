import java.lang.System.getProperty

dependencies {
    arrayOf(
        libs.appium,
    )
        .forEach { testImplementation(it) }
}
tasks {
    test {
        doFirst {
            environment(
                "ANDROID_HOME" to getProperty("ANDROID_HOME"),
                "ANDROID_SDK_ROOT" to getProperty("ANDROID_SDK_ROOT"),
                "APP_APK" to getProperty("APP_APK"),
                "SIMULATOR_UUID" to getProperty("SIMULATOR_UUID").orEmpty(),
                "SIMULATOR_URL" to getProperty("SIMULATOR_URL"),
                "QASE_ENABLE" to getProperty("QASE_ENABLE"),
            )
        }
    }
}
