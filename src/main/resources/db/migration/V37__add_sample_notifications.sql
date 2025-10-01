-- Добавление тестовых уведомлений для пользователя с ID = 1
-- Положительные уведомления (одобренные отзывы)
INSERT INTO notifications (user_id, message, is_read, created_at) VALUES
(1, 'Ваш отзыв на товар "RTX 4090" одобрен и теперь отображается', false, NOW() - INTERVAL '1 day'),
(1, 'Ваш отзыв на товар "Intel Core i9-13900K" одобрен и теперь отображается', false, NOW() - INTERVAL '2 days'),
(1, 'Ваш отзыв на товар "ASUS ROG Strix X670E-E" одобрен и теперь отображается', true, NOW() - INTERVAL '3 days'),
(1, 'Ваш отзыв на товар "Corsair Vengeance LPX 32GB" одобрен и теперь отображается', true, NOW() - INTERVAL '4 days'),
(1, 'Ваш отзыв на товар "Samsung 980 PRO 1TB" одобрен и теперь отображается', false, NOW() - INTERVAL '5 days');

-- Отрицательные уведомления (отклоненные отзывы)
INSERT INTO notifications (user_id, message, is_read, created_at) VALUES
(1, 'Ваш отзыв на товар "MSI GeForce RTX 4080" не прошел модерацию', false, NOW() - INTERVAL '6 days'),
(1, 'Ваш отзыв на товар "AMD Ryzen 9 7950X" не прошел модерацию', true, NOW() - INTERVAL '7 days'),
(1, 'Ваш отзыв на товар "Gigabyte B650 AORUS Elite" не прошел модерацию', false, NOW() - INTERVAL '8 days'),
(1, 'Ваш отзыв на товар "G.Skill Trident Z5 64GB" не прошел модерацию', true, NOW() - INTERVAL '9 days'),
(1, 'Ваш отзыв на товар "WD Black SN850X 2TB" не прошел модерацию', false, NOW() - INTERVAL '10 days');
