package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {
    private final String filename;
    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
    }

    @Override
    public List<Measurement> load() {
        try (InputStream iStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(this.filename)) {
            return List.of(new Gson()
                    .fromJson(new InputStreamReader(Objects.requireNonNull(iStream)),
                            Measurement[].class));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
