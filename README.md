# TaskManager
Приложение, предназначенное для выполнения преднастроенных действий в консоле Windows/Linux. Действия являются расширяемыми. 
## Доступные действия
Наименование действия - параметр вызова приложения без разделителя ":"   
Параметры действия - перечень параметров вызова приложения с разделителем ":"   
Пример вызова:
```
tm "db" "o:push" "c:test comment"
```
>`db` - указывает, что будет выполнена команда работы с базой данных  
>`o` - параметр вызова комманды `db`, значение которого равно `push`  
>`c` - параметр вызова комманды `db`, значение которого равно `test comment`  
### Действие `help`
Вывод информации о приложении
```
tm help
```
### Действие `generate`
Генерация файла. Служит для создания файла новой версии базы данных.
```
tm generate "t:sql" "n:test" "stamp:true" "out:out/my_project"
```
 Краткая нотация:
```
tm g "t:sql"
```
Параметры вызова   
>`t` - тип файла   
>`n` - наименование файла   
>`stamp` - использовать метку времени в именовании файла. Доступные значения true/false. true по умолчанию  
>`out` - директория, где будут создаваться файлы. out по умолчанию 
### Действие `db` 
Выполнение операций с базой данных.
#### Операция `init`
Инициализация базы данных: создание, а также развертывание вспомогательной схемы ver
```
tm db "o:init"
```
#### Операция `remove`
Полное удаление базы данных
```
tm db "o:remove"
```
#### Операция `push`
Обновление базы данных до актуальной версии на основе файлов версий
```
tm db "o:push"
tm db "o:push" "v:20170721160750754" "out:out/my_project"
```
Параметры вызова     
>`out` - директория (включая вложенные в неё директории), где будет произведен поиск файлов-версий. out по умолчанию   
>`v` - установить только указанный файл-версию   

Ответ в виде таблицы со следующими колонками 
>`Timestamp` - временная метка файла-версии   
>`Status` - статус операции(MISSED (установка пропущена), OK(установка успешна), INSTALL(установка была произведена ранее))  
>`Name` - наименование файла-версии  
#### Операция `status`
Получение информации о файлах-версиях базы данных и их состоянии
```
tm "db" "o:status"
```
Ответ в виде таблицы со следующими колонками 
>`Timestamp` - временная метка файла-версии   
>`Local` - статус присутствия файла-версии на локальном диске(TRUE(файл-версия установлен на удаленной бд и присутствует на локальном диске), FALSE(файл-версия установлен на удаленной бд, но не присутствует на локальном диске))    
>`Remote` - статус установки файла-версии на удаленной бд(TRUE(файл-версия установлен на удаленной бд и присутствует на локальном диске), FALSE(файл-версия не установлен на удаленной бд, но присутствует на локальном диске))  
>`Author` - автор файла-версии  
>`Name` - наименование файла-версии 
#### Операция `delete`
Удаление файла-версии базы данных для последующего повторного развертывания. Развернутые артефакты базы данных не удаляются.
```
tm "db" "o:delete" "v:2017072015512882"
```
Для удаления последней развернутой версии используйте команду
```
tm "db" "o:delete"
```
### Действие `remote` (недоступно в текущей версии)
Вызов удаленного сервиса и вывод ответа в консоль
```
TaskManager.exe "remote" "m:GET" "h:login=nik&pass=123" "url=https://api.site.ru/v1/items" "p:name=1&q=test"
```
Параметры вызова   
>`m` - метод запроса GET,POST,PUT,DELETE (GET по умолчанию)   
>`h` - передаваемые заголовки запроса   
>`url` - URL сервиса  
>`p` - передаваемые параметры запроса к сервису  
### Действие `plan` (недоступно в текущей версии)
Выполнение последовательности действий, описанной в JSON файле. Также служит для автоматизации процесса развертывания различных сред
```
TaskManager.exe "plan" "f:init_and_upgrade_db.json"
```
Параметры вызова   
>`f` - файл, описывающий действия и порядок их следования   

Пример файла `init_and_upgrade_db.json`. Описывается сценарий инициализации базы данных и её обновление до последней версии
```
{
  "commands": [
    {
      "name": "db",
      "params": [ "o:init" ]
    },
    {
      "name": "db",
      "params": [ "o:push" ]
    }
  ]
}
```
### Действие `script` (недоступно в текущей версии)
Выполнение скрипта
```
TaskManager.exe "script" "f:deploy.sh"
```
>`f` - скрипт-файл 