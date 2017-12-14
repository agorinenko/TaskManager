package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

import java.util.Set;

public interface Configuration {

    /**
     * Получение элемента конфигурации по ключу
     * @param key ключ
     * @return значение
     */
    Object getEntityByKey(String key);

    Set<String> getKeys();

    boolean containsKey(String key);

    /**
     * Загрузка конфигурации
     */
    void load() throws ConfigurationException;
}
