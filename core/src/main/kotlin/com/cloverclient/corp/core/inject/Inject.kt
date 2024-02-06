package com.cloverclient.corp.core.inject

import io.ktor.server.application.*
import org.glassfish.hk2.api.DynamicConfigurationService
import org.glassfish.hk2.api.ServiceLocator
import org.glassfish.hk2.api.ServiceLocatorFactory
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder

/**
 * @author GrowlyX
 * @since 2/5/2024
 */
lateinit var serviceLocator: ServiceLocator
fun Application.configureInject()
{
    serviceLocator = ServiceLocatorFactory
        .getInstance().create("Platform")
    loadDescriptors()

    log.info("Loaded descriptors")
}

fun loadDescriptors(): Boolean
{
    val dcs = serviceLocator.getService<DynamicConfigurationService>()

    return runCatching {
            dcs.populator.populate(
                ClasspathDescriptorFileFinder(
                    ClassLoader.getSystemClassLoader()
                )
            )
        }.isSuccess
}
