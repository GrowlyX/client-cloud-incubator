package com.cloverclient.corp.core.inject;

import org.glassfish.hk2.utilities.binding.ScopedBindingBuilder;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Utility to bind classes to a BindingBuilder. This is
 * needed as the Kotlin compiler gets confused when selecting
 * which "to" function to use in the BindingBuilder, even though
 * the intended functionality is to use the class function instead
 * of the extension function if it is present.
 *
 * @author GrowlyX
 * @since 6/3/2023
 */
public enum BindingBuilderUtilities {
    INSTANCE;

    @SafeVarargs
    public final <T> void bindTo(
        @NotNull ScopedBindingBuilder<T> builder,
        @NotNull Class<? super T>... clazz
    ) {
        for (final Class<? super T> aClass : clazz) {
            builder.to(aClass);
        }
    }

    @SafeVarargs
    public final <T> void bindTo(
        @NotNull ServiceBindingBuilder<T> builder,
        @NotNull Class<? extends T>... clazz
    ) {
        for (final Class<? extends T> aClass : clazz) {
            builder.to(aClass);
        }
    }

    /**
     * The only difference between this and bindTo(ServiceBindingBuilder<T>...) is
     * the type argument for Class is super rather than extends.
     */
    @SafeVarargs
    public final <T> void bindToSuperService(
        @NotNull ServiceBindingBuilder<T> builder,
        @NotNull Class<? super T>... clazz
    ) {
        for (final Class<? super T> aClass : clazz) {
            builder.to(aClass);
        }
    }
}
