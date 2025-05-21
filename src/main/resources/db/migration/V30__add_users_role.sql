-- Добавляем пользователей
INSERT INTO customers (email, password) VALUES
('admin@example.com', '{bcrypt}$2a$12$QbxV6bBuzcpYtnEtEnCgoesRjXVUNplz.TCsjJNhc2fTJI01dOhuu'), -- password: Password123
('user1@example.com', '{bcrypt}$2a$12$QbxV6bBuzcpYtnEtEnCgoesRjXVUNplz.TCsjJNhc2fTJI01dOhuu'),
('user2@example.com', '{noop}12345');

-- Добавляем роли (связь ManyToOne)
INSERT INTO authorities (name, customer_id) VALUES
('ROLE_ADMIN', 1),
('ROLE_USER', 1),
('ROLE_USER', 2),
('ROLE_USER', 3);

-- Добавляем профили
INSERT INTO profiles (customer_id, first_name, last_name, phone_number, city, address, post_index, birth_date) VALUES
(1, 'Admin', 'Adminov', '+1234567890', 'Moscow', 'Kremlin', 101000, '1980-01-01'),
(2, 'Ivan', 'Petrov', '+7987654321', 'Saint Petersburg', 'Nevsky Prospect', 190000, '1990-05-15'),
(3, 'Maria', 'Ivanova', '+79111234567', 'Kazan', 'Bauman Street', 420000, '1995-10-20');