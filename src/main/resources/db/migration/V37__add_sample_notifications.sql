-- Adding test notifications for user with ID = 1
-- Positive notifications (approved reviews)
INSERT INTO notifications (user_id, message, is_read, created_at) VALUES
(1, 'Your review for product "RTX 4090" has been approved and is now displayed', false, NOW() - INTERVAL '1 day'),
(1, 'Your review for product "Intel Core i9-13900K" has been approved and is now displayed', false, NOW() - INTERVAL '2 days'),
(1, 'Your review for product "ASUS ROG Strix X670E-E" has been approved and is now displayed', true, NOW() - INTERVAL '3 days'),
(1, 'Your review for product "Corsair Vengeance LPX 32GB" has been approved and is now displayed', true, NOW() - INTERVAL '4 days'),
(1, 'Your review for product "Samsung 980 PRO 1TB" has been approved and is now displayed', false, NOW() - INTERVAL '5 days');

-- Negative notifications (rejected reviews)
INSERT INTO notifications (user_id, message, is_read, created_at) VALUES
(1, 'Your review for product "MSI GeForce RTX 4080" did not pass moderation', false, NOW() - INTERVAL '6 days'),
(1, 'Your review for product "AMD Ryzen 9 7950X" did not pass moderation', true, NOW() - INTERVAL '7 days'),
(1, 'Your review for product "Gigabyte B650 AORUS Elite" did not pass moderation', false, NOW() - INTERVAL '8 days'),
(1, 'Your review for product "G.Skill Trident Z5 64GB" did not pass moderation', true, NOW() - INTERVAL '9 days'),
(1, 'Your review for product "WD Black SN850X 2TB" did not pass moderation', false, NOW() - INTERVAL '10 days');
