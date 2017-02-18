package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

public interface Configuration {

    /**
     * Получение элемента конфигурации по ключу
     * @param key ключ
     * @return значение
     */
    Object getEntityByKey(String key);

    /**
     * Загрузка конфигурации
     */
    void load() throws ConfigurationException;
}
