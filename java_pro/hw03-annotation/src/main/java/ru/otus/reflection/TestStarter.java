package ru.otus.reflection;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestStarter {
    private static Class<?> aClass;
    private static int testPass;
    private static int testFail;
    private final ArrayList<Method> before;
    private final ArrayList<Method> test;
    private final ArrayList<Method> after;

    public TestStarter(String className) throws ClassNotFoundException {
        aClass = Class.forName(className);
        this.before = new ArrayList<>();
        this.test   = new ArrayList<>();
        this.after  = new ArrayList<>();
    }

    public void run() {
        Method[] allMethods = aClass.getMethods();
        groupMethods(allMethods);
        runTest(before);
        runTest(test);
        runTest(after);
    }

    public void printResults() {
        System.out.printf("TOTAL = %d \nFAILED = %d \nPASSED = %d%n",
                testFail + testPass, testFail, testPass);
    }

    private void groupMethods(Method[] allMethods) {
        for(Method method : allMethods) {
            if(method.isAnnotationPresent(Before.class)) {
                before.add(method);
            }
            else if(method.isAnnotationPresent(Test.class)) {
                test.add(method);
            }
            else if(method.isAnnotationPresent(After.class)) {
                after.add(method);
            }
        }
    }

    private static void runTest(ArrayList<Method> methods) {
        for(Method method : methods) {
            try {
                Object newInstance = aClass.getDeclaredConstructors()[0].newInstance();
                method.invoke(newInstance);
                testPass++;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                testFail++;
            }
        }
    }
}
