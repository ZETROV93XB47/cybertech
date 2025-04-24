DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS order;
DROP TABLE IF EXISTS orderitem;
DROP TABLE IF EXISTS payment;


CREATE TABLE product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_uuid CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    stock_quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    cpu VARCHAR(255) NOT NULL,
    gpu CHAR(50) NOT NULL,
    ram CHAR(50) NOT NULL,
    hard_drive CHAR(50) NOT NULL,
    ssd CHAR(50) NOT NULL,
    display_type VARCHAR(100),
    display_size INT,
    os CHAR(20) NOT NULL,
    connectivity VARCHAR(255),
    photo VARCHAR(255),
    stock INT,
    description TEXT,
    UNIQUE KEY uk_product_uuid (product_uuid)
);

CREATE TABLE user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email CHAR(50) NOT NULL,
    first_name CHAR(15),
    last_name CHAR(15),
    sex VARCHAR(10) NOT NULL,
    adress CHAR(50),
    birth_date DATE,
    password CHAR(36),
    role VARCHAR(20) NOT NULL,
    UNIQUE KEY uk_user_email (email)
);

CREATE TABLE payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_uuid CHAR(36),
    amount DECIMAL(10, 2),
    payment_method VARCHAR(100),
    payment_status VARCHAR(100),
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE order (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_uuid CHAR(36) NOT NULL,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    user_id BIGINT,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    UNIQUE KEY uk_order_order_uuid (order_uuid)
);

CREATE TABLE order_item (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_item_uuid CHAR(36),
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES order(order_id),
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES product(product_id),
    UNIQUE KEY uk_order_item_uuid (order_item_uuid)
);

CREATE TABLE review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES product(product_id)
);





