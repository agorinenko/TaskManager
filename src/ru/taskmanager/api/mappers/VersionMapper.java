package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;
import ru.taskmanager.api.Version;

public class VersionMapper extends BaseMapper {
    @Override
    protected Row rowObjectInit() {
        return new Version();
    }


}
