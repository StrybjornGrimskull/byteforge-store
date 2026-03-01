-- Создаем sequences
CREATE SEQUENCE customers_id_seq;
CREATE SEQUENCE authorities_id_seq;

-- Создаем таблицу customers
CREATE TABLE customers (
    id INT PRIMARY KEY DEFAULT nextval('customers_id_seq'),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email_verified BOOLEAN DEFAULT FALSE,
    email_verification_token VARCHAR(255),
    password_reset_token VARCHAR(255),
    password_reset_token_expiry TIMESTAMP
);

-- Создаем таблицу authorities
CREATE TABLE authorities (
    id INT PRIMARY KEY DEFAULT nextval('authorities_id_seq'),
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

-- Set sequence ownership
ALTER SEQUENCE customers_id_seq OWNED BY customers.id;
ALTER SEQUENCE authorities_id_seq OWNED BY authorities.id;