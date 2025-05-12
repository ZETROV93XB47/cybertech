-- ========================================================================== --
--                                 USER TABLE                                 --
-- ========================================================================== --
CREATE TABLE user (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à UserEntity (basé sur User.java model)
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100) NOT NULL,
    sex VARCHAR(10), -- Supposant EnumType.STRING
    address VARCHAR(255),
    birth_date DATE,
    password VARCHAR(255) NOT NULL, -- Doit stocker un hash sécurisé
    role VARCHAR(50) NOT NULL, -- Supposant EnumType.STRING

    -- Index pour la recherche par email
    INDEX idx_user_email (email)
);

-- ========================================================================== --
--                               PRODUCT TABLE                                --
-- ========================================================================== --
CREATE TABLE product (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à ProductEntity
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    brand VARCHAR(100) NOT NULL, -- EnumType.STRING
    category VARCHAR(100) NOT NULL, -- EnumType.STRING
    cpu VARCHAR(255) NOT NULL,
    gpu CHAR(50) NOT NULL,
    ram CHAR(50) NOT NULL, -- EnumType.STRING
    ssd CHAR(50) NOT NULL, -- EnumType.STRING
    display_type VARCHAR(100), -- EnumType.STRING, nullable
    display_size VARCHAR(100), -- EnumType.STRING, nullable (était INT avant, mais l'entité dit Enum String)
    os CHAR(20) NOT NULL, -- EnumType.STRING
    connectivity VARCHAR(255), -- nullable
    photo VARCHAR(255), -- nullable
    stock INT NOT NULL, -- int primitif -> NOT NULL
    description TEXT, -- nullable

    -- Index potentiels pour la recherche
    INDEX idx_product_category (category),
    INDEX idx_product_brand (brand),
    INDEX idx_product_name (name)
);

-- ========================================================================== --
--                                ORDER TABLE                                 --
-- ========================================================================== --
-- Utilisation de backticks car 'order' est souvent un mot réservé SQL
CREATE TABLE `order` (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à OrderEntity (basé sur Order.java model)
    order_date DATETIME NOT NULL, -- ou TIMESTAMP
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL, -- Supposant EnumType.STRING

    -- Clé étrangère vers UserEntity (@ManyToOne)
    -- Correction: Utilisation de 
     au lieu de id
    userId BIGINT NOT NULL,

    -- Clé étrangère vers PaymentEntity (@OneToOne, OrderEntity est propriétaire)
    -- Correction: Utilisation de paymentId au lieu de order
    paymentId BIGINT UNIQUE -- UNIQUE car OneToOne, nullable si la relation est optionnelle
);

-- ========================================================================== --
--                             ORDER ITEM TABLE                               --
-- ========================================================================== --
CREATE TABLE order_item (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à OrderItemEntity
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL, -- Prix au moment de la commande

    -- Clé étrangère vers OrderEntity (@ManyToOne)
    orderId BIGINT NOT NULL,

    -- Clé étrangère vers ProductEntity (@ManyToOne)
    -- Correspondant à mappedBy="productEntity" (supposé) dans ProductEntity
    productId BIGINT NOT NULL
);

-- ========================================================================== --
--                              CART ITEM TABLE                               --
-- ========================================================================== --
CREATE TABLE cart_item (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à CartItemEntity
    quantity INT NOT NULL,

    -- Clé étrangère vers UserEntity (un panier appartient à un utilisateur)
    userId BIGINT NOT NULL,

    -- Clé étrangère vers ProductEntity (@ManyToOne)
    -- Correspondant à mappedBy="productEntity" (supposé) dans ProductEntity
    productId BIGINT NOT NULL
);

-- ========================================================================== --
--                                REVIEW TABLE                                --
-- ========================================================================== --
CREATE TABLE review (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à ReviewEntity
    rating INT NOT NULL, -- ex: 1 à 5
    comment TEXT,
    review_date DATETIME NOT NULL, -- ou TIMESTAMP

    -- Clé étrangère vers UserEntity (@ManyToOne)
    userId BIGINT NOT NULL,

    -- Clé étrangère vers ProductEntity (@ManyToOne)
    -- Correspondant à mappedBy="productEntity" dans ProductEntity
    productId BIGINT NOT NULL
);

-- ========================================================================== --
--                               PAYMENT TABLE                                --
-- ========================================================================== --
CREATE TABLE payment (
    -- Colonnes héritées de BaseEntity
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid BINARY(16) NOT NULL UNIQUE,

    -- Colonnes spécifiques à PaymentEntity
    payment_date DATETIME NOT NULL, -- ou TIMESTAMP
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    status VARCHAR(50) NOT NULL -- EnumType.STRING (SUCCESS, FAILED)

    -- Pas de colonne orderId ici car la FK est dans la table 'order' (relation OneToOne où Order est propriétaire)
);


-- ========================================================================== --
--                         FOREIGN KEY CONSTRAINTS                            --
-- ========================================================================== --
-- (Appliquer après la création de toutes les tables)

-- Contraintes pour la table 'order'
ALTER TABLE `order`
    ADD CONSTRAINT fk_order_user FOREIGN KEY (userId) REFERENCES user(id),
    ADD CONSTRAINT fk_order_payment FOREIGN KEY (paymentId) REFERENCES payment(id);

-- Contraintes pour la table 'order_item'
ALTER TABLE order_item
    ADD CONSTRAINT fk_orderitem_order FOREIGN KEY (orderId) REFERENCES `order`(id),
    ADD CONSTRAINT fk_orderitem_product FOREIGN KEY (productId) REFERENCES product(id);

-- Contraintes pour la table 'cart_item'
ALTER TABLE cart_item
    ADD CONSTRAINT fk_cartitem_user FOREIGN KEY (userId) REFERENCES user(id),
    ADD CONSTRAINT fk_cartitem_product FOREIGN KEY (productId) REFERENCES product(id);

-- Contraintes pour la table 'review'
ALTER TABLE review
    ADD CONSTRAINT fk_review_user FOREIGN KEY (userId) REFERENCES user(id),
    ADD CONSTRAINT fk_review_product FOREIGN KEY (productId) REFERENCES product(id);

-- (Pas de contrainte FK à ajouter pour 'payment' dans ce scénario où 'order' possède la FK)
