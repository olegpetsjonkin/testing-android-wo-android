package com.trainline.testing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelper {

    public static void setField(
            @NonNull Object object, @NonNull String fieldName, @Nullable Object valueToSet) {
        Class<?> aClass = object.getClass();
        do {
            try {
                Field field = aClass.getDeclaredField(fieldName);
//                int modifiers = field.getModifiers();
//                if (!Modifier.isPublic(modifiers)) {
//                    throw new RuntimeException("Modified field should be public: " + aClass + "." + fieldName);
//                }
                field.setAccessible(true);
                field.set(object, valueToSet);
                return;
            } catch (NoSuchFieldException e) {
                aClass = aClass.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Can't access field: " + aClass + "." + fieldName, e);
            }
        } while (aClass != null);
        throw new RuntimeException("Can't find field: " + object.getClass() + "." + fieldName);
    }

    @SuppressWarnings({"unchecked", "JavadocReference"})
    public static @NonNull <E extends Enum<E>> E[] valuesExcluding(@NonNull E... toExclude) {
        Class<E> enumClass = TestHelper.getEnumClass(toExclude);
        List<E> result = new ArrayList<>(Arrays.asList(enumClass.getEnumConstants()));
        result.removeAll(Arrays.asList(toExclude));
        return result.toArray((E[]) Array.newInstance(enumClass, result.size()));
    }

    @SuppressWarnings({"unchecked", "JavadocReference"})
    public static @NonNull <E extends Enum<E>> Class<E> getEnumClass(E... enumValues) {
        if (enumValues.length == 0) {
            throw new IllegalArgumentException(
                    "If you don't want to use any values, use YourEnum.values()");
        }
        return (Class<E>) enumValues[0].getClass();
    }
}
