package ru.otus.logging;

public interface TestLogging {
    String calculation(int singleArgument);

    String calculation (int arg1, String arg2);

    String calculation(int param1, int param2);

    String calculation(int param1, int param2, String param3);
}