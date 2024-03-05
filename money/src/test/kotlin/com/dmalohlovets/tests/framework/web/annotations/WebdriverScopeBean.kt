package com.dmalohlovets.tests.framework.web.annotations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

@Bean
@Scope("webdriverscope")
@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@Retention(
    AnnotationRetention.RUNTIME,
)
annotation class WebdriverScopeBean
