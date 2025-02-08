# Library API

## Описание
Это проект CRUD Web API для имитации библиотеки, разработанный с использованием Spring Boot.

## Запуск проекта

1. Убедитесь, что у вас установлен JDK 1.8 или выше.
2. Клонируйте репозиторий и перейдите в директорию проекта.
3. Выполните команду `mvn clean install` для установки зависимостей.
4. Запустите приложение с помощью команды `mvn spring-boot:run`.

## API Эндпоинты

### Book API Endpoints
1. **POST /library/book**
   - Добавляет новую книгу.
   - Request Body: `Book` объект.

2. **GET /library/book/all**
   - Получает все книги.
   - Response: Список объектов `Book`.

3. **GET /library/book/{id}**
   - Получает конкретную книгу по ID.
   - Response: Объект `Book` или 404, если не найдено.

4. **GET /library/book/{isbn}**
   - Получает конкретную книгу по ISBN.
   - Response: Объект `Book` или 404, если не найдено.

5. **PUT /library/book/{id}**
   - Обновляет конкретную книгу по ID.
   - Request Body: Обновленный `Book` объект.
   - Response: Обновленный объект `Book`.

6. **DELETE /library/book/{id}**
   - Удаляет конкретную книгу по ID.
   - Response: 204 No Content.

### Book Tracker API Endpoints
1. **POST /library/book-tracking**
   - Добавляет новый трекер книги.
   - Request Body: `BookTracker` объект.

2. **GET /library/book-tracking/all**
   - Получает всех трекеров книг.
   - Response: Список объектов `BookTracker`.

3. **GET /library/book-tracking/{id}**
   - Получает конкретный трекер книги по ID.
   - Response: Объект `BookTracker` или 404, если не найдено.

4. **PUT /library/book-tracking/{id}**
   - Обновляет конкретный трекер книги по ID.
   - Request Body: Обновленный `BookTracker` объект.
   - Response: 200 OK.

5. **DELETE /library/book-tracking/{id}**
   - Удаляет конкретный трекер книги по ID.
   - Response: 200 OK.
# InternshipLibrary
