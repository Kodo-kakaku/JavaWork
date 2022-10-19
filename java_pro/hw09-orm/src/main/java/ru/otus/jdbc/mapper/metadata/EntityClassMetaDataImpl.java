package ru.otus.jdbc.mapper.metadata;

import ru.otus.jdbc.mapper.annotations.Id;
import ru.otus.jdbc.mapper.exceptions.EntityClassMetaDataException;
import ru.otus.jdbc.mapper.exceptions.NotFoundIdFieldException;
import ru.otus.jdbc.mapper.exceptions.NotUniqueIdFieldException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> klass;

    public EntityClassMetaDataImpl(Class<T> klass) {
        throwExceptionIfInvalidClass(klass);
        this.klass = klass;
    }

    @Override
    public String getName() {
        return klass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return klass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new EntityClassMetaDataException(e);
        }
    }

    @Override
    public Field getIdField() {
        List<Field> idFields = Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) != null)
                .toList();
        if (idFields.isEmpty()) {
            throw new NotFoundIdFieldException();
        } else if (idFields.size() > 1) {
            throw new NotUniqueIdFieldException();
        }

        return idFields.get(0);
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(klass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) == null)
                .toList();
    }

    private void throwExceptionIfInvalidClass(Class<T> klass) {
        int idFieldsCount = Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.getAnnotation(Id.class) != null)
                .toList().size();
        if (idFieldsCount == 0) {
            throw new NotFoundIdFieldException();
        } else if (idFieldsCount > 1) {
            throw new NotUniqueIdFieldException();
        }
    }
}
