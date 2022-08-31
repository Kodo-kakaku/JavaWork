package ru.otus.logging;

import ru.otus.annotations.Log;

public class TestLoggingImpl implements TestLogging {

    @Override
    @Log
    public String calculation(int arg){
        return "Args: int.";
    }

    @Override
    @Log
    public String calculation(int arg1, String arg2){
        return "Args: int, String.";
    }

    @Override
    public String calculation(int arg1, int arg2){
        return  "Args: int, int, whit out annotation.";
    }

    @Override
    @Log
    public String calculation(int arg1, int arg2, String arg3){
        return "Args: int, int, String.";
    }
}