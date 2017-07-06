# TaskManager
Кроссплатформенное приложение, выполненное на языке Java, предназначенное для выполнения преднастроенных действий в консоле Windows/Linux. 
## Доступные действия
Наименование действия - параметр вызова приложения без разделителя ":"   
Параметры действия - перечень параметров вызова приложения с разделителем ":"   
Пример вызова:
```
TaskManager.exe "db" "o:push" "c:test comment"
```
>`db` - указывает, что будет выполнена команда работы с базой данных  
>`o` - параметр вызова комманды `db`, значение которого равно `push`  
>`c` - параметр вызова комманды `db`, значение которого равно `test comment`  
### Действие `help`
Вывод информации о приложении
```
TaskManager.exe "help"
```
### Действие `generate`
Генерация файла. Служит для создания файла новой версии базы данных.
```
TaskManager.exe "generate" "t:sql", "n:test", "stamp:true"
```
 Краткая нотация:
```
TaskManager.exe "generate" "t:sql"
```
### Действие `db` (некоторые операции недоступны в текущей версии)
Выполнение операций с базой данных Postgres. В дальнейшем перечень баз данных будет пополнятся
#### Операция `init`
Инициализация базы данных: создание, а также развертывание вспомогательной схемы ver
```
TaskManager.exe "db" "o:init"
```
#### Операция `init`
Инициализация базы данных: создание, а также развертывание вспомогательной схемы ver
```
TaskManager.exe "db" "o:init"
```
#### Операция `remove`
Полное удаление базы данных
```
TaskManager.exe "db" "o:remove"
```
#### Операция `push`
Обновление базы данных до актуальной версии на основе файлов версий
```
TaskManager.exe "db" "o:push"
```
#### Операция `status`
Получение информации о версиях базы данных и их состоянии
```
TaskManager.exe "db" "o:status"
```
#### Операция `delete`
Удаление версии базы данных для последующего повторного развертывания. Развернутые артифакты базы данных не удаляются. Параметр `v` указывает на версию, которую следует удалить.
```
TaskManager.exe "db" "o:delete" "v:20170522124649370"
```
Для удаления последней развернутой версии используйте команду
```
TaskManager.exe "db" "o:delete"
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
Выполнение скрипт
```
TaskManager.exe "script" "f:deploy.sh"
```
>`f` - скрипт-файл 