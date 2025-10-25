CREATE TABLE prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT,
    brand_name VARCHAR(50),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INTEGER,
    product_id BIGINT,
    priority INTEGER,
    price DECIMAL(10,2),
    curr VARCHAR(3)
);