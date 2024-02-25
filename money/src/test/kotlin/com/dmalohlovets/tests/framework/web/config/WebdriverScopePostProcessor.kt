package com.dmalohlovets.tests.framework.web.config

import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.support.SimpleThreadScope
import org.springframework.stereotype.Component


@Component
class WebdriverScopePostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) =
        beanFactory.registerScope("webdriverscope",
            object : SimpleThreadScope() {
                override fun get(name: String, objectFactory: ObjectFactory<*>): Any {
                    val o = super.get(name, objectFactory) as RemoteWebDriver
                    o.sessionId?.let { return o }
                    super.remove(name)
                    return super.get(name, objectFactory)
                }
            })
}
