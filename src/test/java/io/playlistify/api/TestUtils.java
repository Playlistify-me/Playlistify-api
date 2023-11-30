package io.playlistify.api;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class TestUtils {

    private TestUtils() {
    }

     static void assertPrivateConstructor(Class<?> clazz) throws Exception {
        final String exceptionMessage = clazz.getName() + " does not have a private constructor";

        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();

            Assertions.assertTrue(Modifier.isPrivate(constructor.getModifiers()), exceptionMessage);

            constructor.setAccessible(true);
            constructor.newInstance();
        } catch (NoSuchMethodException e) {
            Assertions.fail(exceptionMessage);
        }
    }
}