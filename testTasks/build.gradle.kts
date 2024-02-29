dependencies {
    arrayOf(
        "org.bitbucket.cowwoc:diff-match-patch:1.2",
        libs.playwright,
    ).forEach { testImplementation(it) }
}
