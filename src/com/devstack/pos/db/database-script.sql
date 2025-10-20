CREATE DATABASE IF NOT EXISTS pos_system_dsmp6;
USE pos_system_dsmp6;
CREATE TABLE IF NOT EXISTS user(
    user_id VARCHAR(80) PRIMARY KEY,
    email VARCHAR(100) UNIQUE,
    display_name VARCHAR(45) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    password VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS product(
    product_id VARCHAR(80) PRIMARY KEY,
    description VARCHAR(100),
    unit_price DOUBLE NOT NULL,
    qty_on_hand INT NOT NULL,
    qr LONGBLOB
);
