CREATE TABLE shopping_cart (
    id SERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    customer_id INT REFERENCES customers(id) ON DELETE CASCADE,
    product_id INT REFERENCES products(id) ON DELETE CASCADE,
    added_date TIMESTAMP
);