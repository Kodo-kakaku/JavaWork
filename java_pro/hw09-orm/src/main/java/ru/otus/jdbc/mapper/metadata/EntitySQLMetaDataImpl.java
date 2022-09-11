package ru.otus.jdbc.mapper.metadata;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private static final int CAPACITY = 60;
    private final String tableName;
    private final List<String> allTableColumns;
    private final List<String> noIdTableColumns;
    private final List<String> idTableColumns;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.tableName = entityClassMetaData.getName();
        this.idTableColumns = List.of(entityClassMetaData.getIdField().getName());
        this.allTableColumns = entityClassMetaData.getAllFields().stream().map(Field::getName).toList();
        this.noIdTableColumns = entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).toList();
    }

    @Override
    public String getSelectAllSql() {
        return addSelectPartSql().append(";").toString();
    }

    @Override
    public String getInsertSql() {
        return addInsertPartSql().append(';').toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder stringBuilder = addSelectPartSql();
        stringBuilder.append(addWherePartSql(this.idTableColumns));
        return stringBuilder.append(';').toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder stringBuilder = addUpdatePartSql();
        stringBuilder.append(addWherePartSql(this.noIdTableColumns));
        return stringBuilder.append(";").toString();
    }

    private StringBuilder addSelectPartSql() {
        StringBuilder stringBuilder = new StringBuilder(CAPACITY);
        stringBuilder.append("SELECT ");
        stringBuilder.append(this.allTableColumns.get(0));
        for (int i = 1; i < this.allTableColumns.size(); i++) {
            stringBuilder.append(", ")
                    .append(this.allTableColumns.get(i));
        }
        return stringBuilder.append(" FROM ").append(this.tableName).append("\n");
    }

    private StringBuilder addWherePartSql(List<String> columns) {
        StringBuilder stringBuilder = new StringBuilder(CAPACITY);
        stringBuilder.append("where ");
        stringBuilder.append(columns.get(0)).append(" = ?");
        for (int i = 1; i < columns.size(); i++) {
            stringBuilder.append(columns.get(i)).append(" = ?");
        }
        return stringBuilder.append("\n");
    }

    private StringBuilder addInsertPartSql() {
        StringBuilder stringBuilder = new StringBuilder(CAPACITY);
        stringBuilder.append("INSERT INTO ").append(this.tableName).append("(");
        stringBuilder.append(this.noIdTableColumns.get(0));

        for (int i = 1; i < this.noIdTableColumns.size(); i++) {
            stringBuilder.append(", ").append(this.noIdTableColumns.get(i));
        }

        stringBuilder.append(") VALUES(?");
        stringBuilder.append(", ?".repeat(this.noIdTableColumns.size() - 1));
        stringBuilder.append(")\n");
        return stringBuilder;
    }

    private StringBuilder addUpdatePartSql() {
        StringBuilder stringBuilder = new StringBuilder(CAPACITY);
        stringBuilder.append("UPDATE ").append(this.tableName).append(" SET ");
        stringBuilder.append(this.allTableColumns.get(0)).append(" = ?");
        for (int i = 1; i < this.allTableColumns.size(); i++) {
            stringBuilder.append(", ").append(this.allTableColumns.get(i)).append(" = ?");
        }
        return stringBuilder.append("\n");
    }
}
