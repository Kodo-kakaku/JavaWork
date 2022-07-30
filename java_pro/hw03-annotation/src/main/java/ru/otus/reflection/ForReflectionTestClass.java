package ru.otus.reflection;

import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.annotation.After;

public class ForReflectionTestClass {
    @Before
    public void printBefore() {
        System.out.println("methodBefore()");
    }

    @Test
    public void printTest() {
        System.out.println("methodTest()");
    }

    @After
    public void printAfter() {
        System.out.println("methodAfter()");
    }
}
