-- Create sequences
CREATE SEQUENCE orders_id_seq;
CREATE SEQUENCE order_products_id_seq;

CREATE TABLE orders (
    id BIGINT PRIMARY KEY DEFAULT nextval('orders_id_seq'),
    total_price NUMERIC(12,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    post_index INTEGER NOT NULL,
    customer_id INT,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE order_products (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_products_id_seq'),
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Set sequence ownership
ALTER SEQUENCE orders_id_seq OWNED BY orders.id;
ALTER SEQUENCE order_products_id_seq OWNED BY order_products.id;