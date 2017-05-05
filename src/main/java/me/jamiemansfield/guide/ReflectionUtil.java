/*
 * This file is part of Guide, licensed under the MIT License (MIT).
 *
 * Copyright (c) Jamie Mansfield <https://www.jamierocks.uk/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.jamiemansfield.guide;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility for using Reflection.
 */
public final class ReflectionUtil {

    /**
     * Sets the value of a <code>static final</code> field in the given class.
     *
     * @param clazz The class
     * @param field The name of the field
     * @param object The new value
     * @throws Exception If something goes wrong
     */
    public static void setStaticFinalField(Class clazz, String field, Object object) throws Exception {
        try {
            Field instanceField = clazz.getDeclaredField(field);
            instanceField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(instanceField, instanceField.getModifiers() & ~Modifier.FINAL);

            instanceField.set(null, object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Exception("Failed to set field: " + field + " in class: " + clazz.getName(), e);
        }
    }

    /**
     * Gets a {@link List} of all the {@link Method}s in the given class, annotated with the given annotation class.
     *
     * @param clazz The class
     * @param annotClazz The annotation class
     * @return The list of methods
     */
    public static List<Method> getMethodsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotClazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getClass().isAnnotationPresent(annotClazz))
                .collect(Collectors.toList());
    }

    /**
     * Gets a {@link List} of all the {@link Field}s in the given class, annotated with the given annotation class.
     *
     * @param clazz The class
     * @param annotClazz The annotation class
     * @return The list of fields
     */
    public static List<Field> getFieldsAnnotatedWith(Class<?> clazz, Class<? extends Annotation> annotClazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.getClass().isAnnotationPresent(annotClazz))
                .collect(Collectors.toList());
    }

    /**
     * Gets a {@link List} of all the superclasses of the given {@link Class}.
     *
     * @param clazz The class to search
     * @return The superclasses of the given class
     */
    public static List<Class> getSuperclassesOf(final Class clazz) {
        final List<Class> classpathClasses = new ArrayList<>();

        // If there is a superclass, add it and its superclasses
        if (clazz.getSuperclass() != null) {
            classpathClasses.add(clazz.getSuperclass());
            classpathClasses.addAll(getSuperclassesOf(clazz.getSuperclass()));
        }

        // For each superinterface, add it and its superclasses
        for (final Class superInterface : clazz.getInterfaces()) {
            classpathClasses.add(superInterface);
            classpathClasses.addAll(getSuperclassesOf(superInterface));
        }

        return classpathClasses;
    }

    private ReflectionUtil() {
    }

}
