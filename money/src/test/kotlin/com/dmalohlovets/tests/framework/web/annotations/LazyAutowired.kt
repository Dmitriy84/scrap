package com.dmalohlovets.tests.framework.web.annotations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy


@Lazy
@Autowired
@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LazyAutowired
