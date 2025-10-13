# ByteForge Online Store

ByteForge - это интернет-магазин компьютерных комплектующих, построенный на Spring Boot с современной архитектурой и безопасностью.

## 🚀 Особенности

- **Spring Boot 3.5.5** с Java 21
- **PostgreSQL** база данных
- **JWT аутентификация** с HttpOnly cookies
- **Spring Security** с ролевой моделью доступа
- **Email сервис** для подтверждения регистрации и уведомлений
- **Thymeleaf** для веб-интерфейса
- **REST API** для мобильных приложений
- **Docker Compose** для легкого развертывания

## 🛠 Технологический стек

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: PostgreSQL 15
- **Frontend**: Thymeleaf, Bootstrap, JavaScript
- **Authentication**: JWT tokens
- **Email**: SMTP (настроен для MailHog в разработке)
- **Build**: Maven
- **Containerization**: Docker Compose

## 📋 Требования

- Java 21+
- Maven 3.6+
- Docker & Docker Compose
- PostgreSQL 15 (или используйте Docker)

## 🚀 Быстрый старт

### 1. Клонирование репозитория
```bash
git clone https://github.com/StrybjornGrimskull/byteforge-store
cd byteforge
```

### 2. Запуск базы данных
```bash
docker-compose up -d db
```

### 3. Сборка и запуск приложения
```bash
# Сборка проекта
./mvnw clean install

# Запуск приложения
./mvnw spring-boot:run
```

### 4. Доступ к приложению
- **Веб-интерфейс**: https://localhost:8443
- **API**: https://localhost:8443/api

⚠️ **Важно**: Приложение использует самоподписанный SSL сертификат для разработки. При первом заходе браузер покажет предупреждение о безопасности. Это нормально для локальной разработки:

1. **Chrome/Edge**: Нажмите "Дополнительно" → "Перейти на localhost (небезопасно)"
2. **Firefox**: Нажмите "Дополнительно" → "Принять риск и продолжить"
3. **Safari**: Нажмите "Показать подробности" → "Посетить этот веб-сайт"

Для продакшена обязательно замените самоподписанный сертификат на реальный!

## 📧 Настройка Email сервиса

### Для разработки (MailHog)
```bash
# Запуск MailHog
docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog

# Доступ к MailHog UI: http://localhost:8025
```

### Для продакшена
Обновите настройки в `application_prod.properties`:
```properties
spring.mail.host=your-smtp-host
spring.mail.port=587
spring.mail.username=your-email@domain.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 🗄 База данных

### Структура базы данных
- **Customers** - пользователи системы
- **Products** - товары (процессоры, видеокарты, память и т.д.)
- **Orders** - заказы
- **Reviews** - отзывы на товары
- **Wishlist** - списки желаний
- **Notifications** - уведомления

### Миграции
Приложение использует Flyway для управления миграциями базы данных:
```bash
# Миграции автоматически применяются при запуске
./mvnw spring-boot:run
```

## 🔐 Безопасность

### Роли пользователей
- **USER** - обычный пользователь
- **ADMIN** - администратор
- **MODERATOR** - модератор
- **PRODUCT_MANAGER** - менеджер товаров

### JWT токены
- Токены хранятся в HttpOnly cookies
- Время жизни: 24 часа
- Автоматическое обновление при активности

## 🧪 Тестирование

Пользователи для тестов
- admin@example.com / Milesisuq@12032
- moderator@example.com / Milesisuq@12032
- product_manager@example.com / Milesisuq@12032
-
## 📁 Структура проекта

```
src/
├── main/
│   ├── java/com/byteforge/byteforge/
│   │   ├── configuration/     # Конфигурация Spring
│   │   ├── constants/         # Константы приложения
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── entities/         # JPA сущности
│   │   ├── exceptions/       # Кастомные исключения
│   │   ├── filter/           # JWT фильтр
│   │   ├── repositories/     # JPA репозитории
│   │   ├── services/         # Бизнес-логика
|   |   ├── utils /           # Вспомогательные классы
│   │   └── web/              # Контроллеры
│   └── resources/
│       ├── db/migration/     # Flyway миграции
│       ├── static/           # Статические ресурсы
│       └── templates/        # Thymeleaf шаблоны
└── test/                     # Тесты
```

### SSL сертификат
Для HTTPS используется самоподписанный сертификат `keystore.p12`:
- Пароль: `changeit`
- Для продакшена замените на реальный сертификат

---

**ByteForge** - Ваш надежный партнер в мире компьютерных технологий! 🖥️💻
