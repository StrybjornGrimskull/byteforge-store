-- Create sequence
CREATE SEQUENCE reviews_id_seq;

CREATE TABLE reviews (
    id BIGINT PRIMARY KEY DEFAULT nextval('reviews_id_seq'),
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    customer_id INT NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    user_first_name VARCHAR(100) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    text TEXT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ограничение: один отзыв на продукт от одного пользователя
CREATE UNIQUE INDEX uniq_review_per_product_per_customer ON reviews(product_id, customer_id); 

-- Set sequence ownership
ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id; 