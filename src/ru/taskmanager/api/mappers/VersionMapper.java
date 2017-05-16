package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;
import ru.taskmanager.api.Version;

public class VersionMapper extends BaseMapper {

    @Override
    protected Row createInstanceOfRow() {
        return new Version();
    }

    @Override
    public boolean insert(Row row) {
        return false;
    }

    @Override
    public boolean update(Row row) {
        return false;
    }
}
