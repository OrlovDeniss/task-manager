# Task manager
Менеджер задач


## Стэк
Java 17, Maven, Spring Boot 3, Spring Data, PostgreSQl, JUnit, Mockito

## API
URL: http://localhost:8080

- POST /tasks - создать задачу
- GET /tasks - получить список всех задач
- PUT /tasks/{id} - обновить задачу пр id
- GET /tasks/{id} - получить задачу по id
- GET /tasks/{id} - удалить задачу по id


Подробнее: http://localhost:8080/swagger-ui/index.html

## Сборка и запуск
1. Скопируйте репозиторий:
```Bash
git clone https://github.com/OrlovDeniss/task-manager.git
```
2. Перейдите в каталог проекта: 
```Bash
cd task-manager
```
3. Скомпилируйте исходные файлы:
```Bash
mvn clean package
```
4. Запустите проект из папки target:
```Bash
java -jar task-manager-0.0.1-SNAPSHOT.jar
```