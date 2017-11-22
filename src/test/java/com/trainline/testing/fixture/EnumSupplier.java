package com.trainline.testing.fixture;

import android.support.annotation.NonNull;
import com.flextrade.jfixture.JFixture;
import com.flextrade.jfixture.SpecimenBuilder;
import com.flextrade.jfixture.SpecimenSupplier;
import com.flextrade.jfixture.customisation.Customisation;
import com.flextrade.jfixture.customisation.OmitAutoPropertyCustomisation;
import com.flextrade.jfixture.utility.SpecimenType;
import com.trainline.testing.TestHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * Usage, listed in preferred usage order; maybe in some cases you may want to be more explicit.
 * All values from enum:
 * <code><pre>
 * // Actually there's no need to do any customization in this case, as it's the default behavior.
 * //fixture.customise().lazyInstance(EnumType.class, EnumSupplier.including(fixture, EnumType.values()));
 * </pre></code>
 * All values listed in the invocation lazily used as random values:
 * <code><pre>
 * fixture.customise(EnumSupplier.including(EnumType.CONSTANT1, EnumType.CONSTANT2));
 * fixture.customise().lazyInstance(EnumType.class,
 *         EnumSupplier.including(fixture, EnumType.CONSTANT1, EnumType.CONSTANT2));
 * </pre></code>
 * All values except the listed ones:
 * <code><pre>
 * fixture.customise(EnumSupplier.excluding(EnumType.CONSTANT1, EnumType.CONSTANT2));
 * fixture.customise().lazyInstance(EnumType.class,
 *         EnumSupplier.excluding(fixture, EnumType.CONSTANT1, EnumType.CONSTANT2));
 * </pre></code>
 *
 * @see TestHelper#valuesExcluding(Enum[]) for more specific usages
 */
public class EnumSupplier<E extends Enum<E>> implements SpecimenSupplier<E> {
    private final @NonNull
    JFixture fixture;
    private final @NonNull E[] values;

    @SafeVarargs
    private EnumSupplier(@NonNull JFixture fixture, @NonNull E... values) {
        this.fixture = fixture;
        this.values = values;
    }

    @Override
    public E create() {
        return values.length == 0 ? null :
                fixture.create().fromList(values);
    }

    @SafeVarargs
    public static @NonNull <E extends Enum<E>> SpecimenSupplier<E> including(
            @NonNull JFixture fixture, @NonNull E... included) {
        return new EnumSupplier<>(fixture, included);
    }

    @SafeVarargs
    public static @NonNull <E extends Enum<E>> Customisation including(@NonNull E... included) {
        return new EnumSupplierCustomisation<>(TestHelper.getEnumClass(included), included);
    }

    @SafeVarargs
    public static @NonNull <E extends Enum<E>> SpecimenSupplier<E> excluding(
            @NonNull JFixture fixture, @NonNull E... excluded) {
        return new EnumSupplier<>(fixture, TestHelper.valuesExcluding(excluded));
    }

    @SafeVarargs
    public static @NonNull <E extends Enum<E>> Customisation excluding(@NonNull E... excluded) {
        return new EnumSupplierCustomisation<>(TestHelper.getEnumClass(excluded), TestHelper.valuesExcluding(excluded));
    }

    /**
     * @see com.flextrade.jfixture.customisation.InstanceFactoryCustomisation
     */
    private static class EnumSupplierCustomisation<E extends Enum<E>> implements Customisation {

        private final SpecimenType<E> instanceType;
        private final E[] values;

        @SafeVarargs
        private EnumSupplierCustomisation(Class<E> instanceType, E... values) {
            this.instanceType = SpecimenType.of(instanceType);
            this.values = values;
        }

        @Override
        public void customise(JFixture fixture) {
            EnumSupplier<E> specimenSupplier = new EnumSupplier<>(fixture, values);

            // below copied from InstanceFactoryCustomisation
            SpecimenBuilder builder = newCustomBuilder(this.instanceType, specimenSupplier);

            fixture.addBuilderToStartOfPipeline(builder);
            fixture.customise(new OmitAutoPropertyCustomisation(this.instanceType));
        }

        private SpecimenBuilder newCustomBuilder(Type instanceType, SpecimenSupplier<E> supplier) {
            try {
                // CustomBuilder is package private, and EnumSupplier needs a fixture
                // so InstanceFactoryCustomisation cannot be used, because it needs a supplier ready
                // hence this is a mimic of that class with lazy instantiation of the supplier
                Constructor<?> CustomBuilder = Class
                        .forName("com.flextrade.jfixture.customisation.CustomBuilder")
                        .getConstructor(Type.class, SpecimenSupplier.class);
                CustomBuilder.setAccessible(true);
                return (SpecimenBuilder) CustomBuilder.newInstance(instanceType, supplier);
            } catch (Exception e) {
                throw new IllegalStateException("Cannot create CustomBuilder", e);
            }
        }
    }
}
