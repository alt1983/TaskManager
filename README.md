#Приложение Система управления задачами

##Запуск
Приложение запускается на порту 8080

##Авторизация
POST http://localhost:8080/login

##Вывод списка задач
GET http://localhost:8080/list

##Добавление новой задачи
POST http://localhost:8080/task

##Изменение описания задачи
POST http://localhost:8080/description

##Изменение приоритета задачи
POST http://localhost:8080/priority

##Изменение исполнителя задачи
POST http://localhost:8080/executor

##Изменение статуса созданной пользователем задачи 
POST http://localhost:8080/statusauthor

##Изменение статуса задачи исполнителем
POST http://localhost:8080/statusexecutor

##Удаление задачи
DELETE http://localhost:8080/task

##Выход
POST http://localhost:8080/logout

##Тестирование
* Реализован набор тестов с использованием Mockito и JUnit
* Реализован набор тестов с использованием Testcontainers
* Для добства тестирования создан Docker-image cloud

##Тестовые пользователи
* USER: user1/user1
* ADMIN: admin1/admin1



