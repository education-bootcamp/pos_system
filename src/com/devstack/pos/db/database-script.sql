CREATE DATABASE IF NOT EXISTS pos_system_dsmp6;
USE pos_system_dsmp6;
CREATE TABLE IF NOT EXISTS user(
    user_id VARCHAR(80) PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    display_name VARCHAR(45) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    password VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS customer(
    customer_id VARCHAR(80) PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(100) NOT NULL,
    salary DOUBLE NOT NULL
);
CREATE TABLE IF NOT EXISTS product(
    product_id VARCHAR(80) PRIMARY KEY,
    description VARCHAR(100),
    unit_price DOUBLE NOT NULL,
    qty_on_hand INT NOT NULL,
    qr LONGBLOB
);
CREATE TABLE IF NOT EXISTS orders(
    order_id INT PRIMARY KEY,
    customer_id VARCHAR(80),
    total_cost DOUBLE NOT NULL,
    date DATETIME NOT NULL,
    CONSTRAINT FOREIGN KEY(customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS order_details(
    order_id INT,
    product_id VARCHAR(80),
    unit_price DOUBLE NOT NULL,
    qty INT NOT NULL,
    date DATETIME NOT NULL,
    CONSTRAINT PRIMARY KEY(order_id, product_id),
    CONSTRAINT FOREIGN KEY(order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(product_id) REFERENCES product(product_id) ON DELETE CASCADE ON UPDATE CASCADE
);
