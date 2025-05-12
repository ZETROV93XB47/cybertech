-- Désactiver temporairement les vérifications de clés étrangères pour le DROP
SET FOREIGN_KEY_CHECKS=0;

-- Supprimer les tables si elles existent (dans l'ordre inverse des dépendances ou avec CASCADE si supporté)
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS `order_table`; -- 'order' est un mot-clé SQL, donc on l'échappe
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS cart_table; -- Nom de la table de CartEntity
DROP TABLE IF EXISTS product_table; -- Nom de la table de ProductEntity
DROP TABLE IF EXISTS `user_table`; -- Supposons que UserEntity utilise user_table

-- Réactiver les vérifications de clés étrangères
SET FOREIGN_KEY_CHECKS=1;

-- Création de la table User (supposée UserEntity)
CREATE TABLE `user_table` (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à UserEntity (basé sur User.java model)
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100) NOT NULL,
    sex VARCHAR(10), -- Pour l'enum Sex
    address VARCHAR(255),
    birth_date DATE,
    password VARCHAR(255) NOT NULL, -- Devrait stocker un hash sécurisé
    role VARCHAR(50) NOT NULL, -- Pour l'enum Role

    INDEX idx_user_email (email)
);

-- Création de la table Product
CREATE TABLE product_table (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à ProductEntity
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    cpu VARCHAR(255) NOT NULL,
    gpu CHAR(50) NOT NULL,
    ram CHAR(50) NOT NULL,
    ssd CHAR(50) NOT NULL,
    display_type VARCHAR(100),
    display_size VARCHAR(100),
    os CHAR(20) NOT NULL,
    connectivity VARCHAR(255),
    photo VARCHAR(255),
    stock INT NOT NULL DEFAULT 0,
    description TEXT,

    INDEX idx_product_category (category),
    INDEX idx_product_brand (brand)
);

-- Création de la table Cart
CREATE TABLE cart_table (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à CartEntity
    user_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    is_checked_out BOOLEAN NOT NULL DEFAULT FALSE
);

-- Création de la table CartItem (supposée CartItemEntity)
CREATE TABLE cart_item (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1
);

-- Création de la table Payment (supposée PaymentEntity)
CREATE TABLE payment (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques
    payment_date DATETIME NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    status VARCHAR(50) NOT NULL -- Pour l'enum PaymentStatus (SUCCESS, FAILED)
);

-- Création de la table Order (supposée OrderEntity)
CREATE TABLE `order_table` (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques
    user_id BIGINT NOT NULL,
    payment_id BIGINT UNIQUE, -- Une commande peut être liée à un paiement (OneToOne)
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL -- Pour l'enum OrderStatus
);

-- Création de la table OrderItem (supposée OrderItemEntity)
CREATE TABLE order_item (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL -- Prix au moment de la commande
);

-- Création de la table Review (supposée ReviewEntity)
CREATE TABLE review (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    rating INT NOT NULL, -- ex: 1 à 5
    comment TEXT,
    review_date DATETIME NOT NULL
);


-- Définition des clés étrangères
-- (Appliquer après la création de toutes les tables pour éviter les dépendances circulaires)

ALTER TABLE cart_table
    ADD CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES `user_table`(id) ON DELETE CASCADE;

ALTER TABLE cart_item
    ADD CONSTRAINT fk_cartitem_cart FOREIGN KEY (cart_id) REFERENCES cart_table(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_cartitem_product FOREIGN KEY (product_id) REFERENCES product_table(id) ON DELETE CASCADE;

ALTER TABLE `order_table`
    ADD CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES `user_table`(id) ON DELETE RESTRICT, -- ou CASCADE selon la logique métier
    ADD CONSTRAINT fk_order_payment FOREIGN KEY (payment_id) REFERENCES payment(id) ON DELETE SET NULL; -- Si un paiement est supprimé, la commande n'est plus liée

ALTER TABLE order_item
    ADD CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES `order_table`(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES product_table(id) ON DELETE RESTRICT;

ALTER TABLE review
    ADD CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES `user_table`(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES product_table(id) ON DELETE CASCADE;
