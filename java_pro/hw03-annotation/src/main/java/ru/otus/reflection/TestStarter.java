package ru.otus.reflection;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class TestStarter {
    public static void start(String classname) throws ClassNotFoundException {
        AtomicInteger pass = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();

        Class<?> aClass = Class.forName(classname);
        Method[] methods = aClass.getMethods();

        Method[][] allPresentMethods = {
                Arrays.stream(methods)
                        .filter(x -> x.isAnnotationPresent(Before.class))
                        .toArray(Method[]::new),
                Arrays.stream(methods)
                        .filter(x -> x.isAnnotationPresent(Test.class))
                        .toArray(Method[]::new),
                Arrays.stream(methods)
                        .filter(x -> x.isAnnotationPresent(After.class))
                        .toArray(Method[]::new)
        };

        Arrays.stream(allPresentMethods)
                .flatMap(Stream::of)
                .forEach(method -> {
                    try {
                        Object newInstance = aClass.getDeclaredConstructors()[0].newInstance();
                        method.invoke(newInstance);
                        pass.getAndIncrement();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        fail.getAndIncrement();
                    }
                });

        System.out.printf("TOTAL = %s \nFAILED = %s \nPASSED = %s%n",
                fail.get() + pass.get(), fail.get(), pass.get());
    }
}
