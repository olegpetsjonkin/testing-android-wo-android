package com.trainline.testing.fixture;

import android.support.annotation.NonNull;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.SpecimenSupplier;

/**
 * This could be replaced with
 * {@code fixture.customise().useSubType(Base.class, Impl1.class, Impl2.class);}
 * but that method doesn't have a varargs overload yet.
 *
 * Usage:
 * <code>
 * fixture.customise().lazyInstance(Base.class, new ImplementationSupplier(fixture, Impl1.class, Impl2.class));
 * </code>
 */
public class ImplementationSupplier<T> implements SpecimenSupplier<T> {
    private final @NonNull JFixture fixture;
    private final @NonNull Class<? extends T>[] implementations;

    @SafeVarargs
    public ImplementationSupplier(@NonNull JFixture fixture, @NonNull Class<? extends T>... implementations) {
        this.fixture = fixture;
        this.implementations = implementations;
    }

    @Override
    public @NonNull T create() {
        Class<? extends T> pickedImplementation = fixture.create().fromList(implementations);
        return fixture.create(pickedImplementation);
    }
}
