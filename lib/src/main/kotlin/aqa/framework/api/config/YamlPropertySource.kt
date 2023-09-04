package aqa.framework.api.config

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

class YamlPropertySource : PropertySourceFactory {
    override fun createPropertySource(
        name: String?,
        encodedResource: EncodedResource
    ): PropertySource<*> {
        val properties = YamlPropertiesFactoryBean().apply {
            setResources(encodedResource.resource)
        }.getObject()!!
        return encodedResource.resource.filename?.let { PropertiesPropertySource(it, properties) }!!
    }
}
