package ru.otus.dataprocessor;

import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try(Writer file = new FileWriter(this.fileName)) {
            new GsonBuilder().create().toJson(data, file);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
