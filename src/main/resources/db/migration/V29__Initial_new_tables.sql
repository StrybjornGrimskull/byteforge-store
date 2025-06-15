-- Создаем таблицу customers
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email_verified BOOLEAN DEFAULT FALSE,
    email_verification_token VARCHAR(255)
);

-- Создаем таблицу authorities
CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    customer_id INT REFERENCES customers(id) ON DELETE CASCADE
);

-- Создаем таблицу профилей
CREATE TABLE profiles (
    customer_id INT PRIMARY KEY REFERENCES customers(id) ON DELETE CASCADE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(20),
    city TEXT,
    address TEXT,
    post_index INT,
    birth_date DATE
);