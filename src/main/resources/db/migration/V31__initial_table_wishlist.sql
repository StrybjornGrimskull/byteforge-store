-- Create sequence
CREATE SEQUENCE wishlist_items_id_seq;

CREATE TABLE wishlist_items (
    id INT PRIMARY KEY DEFAULT nextval('wishlist_items_id_seq'),
    customer_id INT REFERENCES customers(id) ON DELETE CASCADE,
    product_id INT REFERENCES products(id) ON DELETE CASCADE,
    added_date TIMESTAMP
);

-- Set sequence ownership
ALTER SEQUENCE wishlist_items_id_seq OWNED BY wishlist_items.id;