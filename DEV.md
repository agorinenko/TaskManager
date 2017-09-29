# TaskManager
## Системные требования
>`Java SE Development Kit 8` для разработки и тестирования  
>`IntelliJ IDEA Community` среда разработки

Убедитесь что у вас установлена `Java SE Development Kit 8` для разработки и тестирования. Выполните
```
java -version
```
Пример, результата вывода 
```
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)
```
## Настройка проекта
2. Откройте проект в среде разработки 
3. Откройте File | Project Structure | Modules
4. Создайте новый модуль Java на папку TaskManager, указав в качестве зависимостей jar файлы `IntelliJ IDEA/lib/hamcrest-core-1.3.jar` и `IntelliJ IDEA/lib/junit-4.12.jar`
5. Создайте новую конфигурацию JUnit. Файлы тестов располагаются в папке `TaskManager\src\ru\taskmanager\tests\args` проекта. 
Для корректного прохождения тестов настройте конфугурацию запуска скриптов, указав там параметр среды TM_HOME=D:\src\TaskManager, 
где "D:\src\TaskManager" папка, где находится проект.