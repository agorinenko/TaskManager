package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;
import ru.taskmanager.api.Version;

public class VersionMapper extends BaseMapper {

    @Override
    protected Row createInstanceOfRow() {
        return new Version();
    }

    @Override
    public int insert(Row row) {
        return -1;
    }

    @Override
    public boolean update(Row row) {
        return false;
    }
}
