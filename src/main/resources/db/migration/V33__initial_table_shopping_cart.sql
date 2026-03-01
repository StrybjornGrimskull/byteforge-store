-- Create sequence
CREATE SEQUENCE shopping_cart_id_seq;

CREATE TABLE shopping_cart (
    id INT PRIMARY KEY DEFAULT nextval('shopping_cart_id_seq'),
    quantity INT NOT NULL,
    customer_id INT REFERENCES customers(id) ON DELETE CASCADE,
    product_id INT REFERENCES products(id) ON DELETE CASCADE,
    added_date TIMESTAMP
);

-- Set sequence ownership
ALTER SEQUENCE shopping_cart_id_seq OWNED BY shopping_cart.id;