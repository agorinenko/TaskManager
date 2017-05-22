package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;
import ru.taskmanager.api.Version;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class VersionMapper extends BaseMapper {

    @Override
    protected Row createInstanceOfRow() {
        return new Version();
    }

    @Override
    public int insert(Row row) {
        throw new NotImplementedException();
    }
}
