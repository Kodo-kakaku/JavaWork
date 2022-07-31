package ru.otus.reflection;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestStarter {
    private static Class<?> aClass;
    private static int testPass;
    private static int testFail;
    private final List<Method> before;
    private final List<Method> test;
    private final List<Method> after;

    public TestStarter(String className) throws ClassNotFoundException {
        aClass = Class.forName(className);
        this.before = new ArrayList<>();
        this.test   = new ArrayList<>();
        this.after  = new ArrayList<>();
    }

    public void run() {
        Method[] allMethods = aClass.getMethods();
        groupMethods(allMethods);
        runTest(this.before, this.test, this.after);
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

    private static void runTest(List<Method> before, List<Method> tests, List<Method> after) {
        for(Method test : tests) {
            try {
                Object newInstance = aClass.getDeclaredConstructor().newInstance();
                for(Method b : before) { b.invoke(newInstance);}
                test.invoke(newInstance);
                for(Method a : after) { a.invoke(newInstance); }
                testPass++;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                testFail++;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
