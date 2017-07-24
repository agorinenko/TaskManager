# TaskManager
## Системные требования
>`Java SE Runtime Environment 8` исполняемая среда  
>`Java SE Development Kit 8` для разработки и тестирования(опционально)  
>`IntelliJ IDEA Community` среда разработки(опционально)

Убедитесь что у вас установлена исполняемая среда `Java SE Runtime Environment 8`. Выполните
```
java -version
```
Пример, результата вывода 
```
java version "1.8.0_131"
Java(TM) SE Runtime Environment (build 1.8.0_131-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.131-b11, mixed mode)
```
## Получение и установка дистрибутива
1. [Скачайте](./distr/tm_distr.zip) последнюю версию дистрибутива
2. Распакуйте архив в папку на жестком диске
3. Добавьте путь к папке в системную переменную `Path`
4. Создайте системную переменную `TM_HOME`, указав в качестве значения путь к папке дистрибутивов
Перезагрузите CMD, выполните
```
tm help
```
Пример, результата вывода 
```
========= Task manager console 1.0.0 =========
See full description on
https://github.com/agorinenko/TaskManager/#taskmanager
==============================================
```
## Ручная сборка дистрибутива
Для ручной сборки артефактов установите `Java SE Development Kit 8` и `IntelliJ IDEA Community`  
1. Выполните настройку проекта [руководство](DEV.md)
2. Откройте проект в среде разработки
3. Откройте File | Project Structure | Artifacts | JAR - From modules with dependencies
4. Укажите ru.taskmanager.Main в качестве главного класса
5. Выполните Build | Build Artifact
6. Скопируйте полученный jar, tm.bat и папку settings из репозитория проекта в папку на жестком диске
7. Добавьте путь к папке в системную переменную `Path`
8. Создайте системную переменную `TM_HOME`, указав в качестве значения путь к папке дистрибутивов
Перезагрузите CMD, выполните
```
tm help
```
Пример, результата вывода 
```
========= Task manager console 1.0.0 =========
See full description on
https://github.com/agorinenko/TaskManager/#taskmanager
==============================================
```