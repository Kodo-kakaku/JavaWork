package ru.otus;

import ru.otus.reflection.TestStarter;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        TestStarter testStarter = new TestStarter("ru.otus.reflection.ForReflectionTestClass");
        testStarter.run();
        testStarter.printResults();
    }
}