package ru.otus;

import ru.otus.logging.ProxyLog;
import ru.otus.logging.TestLogging;
import ru.otus.logging.TestLoggingImpl;

public class Main {
    public static void main(String[] args) {
        String stripe = "\n-----------------------------";
        final TestLogging testLogging = ProxyLog.createProxedClass(TestLoggingImpl.class);

        System.out.println(testLogging.calculation(1) + stripe);
        System.out.println(testLogging.calculation(1, "arg") + stripe);
        System.out.println(testLogging.calculation(1, 2) + stripe);
        System.out.println(testLogging.calculation(1, 2, "3") + stripe);
    }
}