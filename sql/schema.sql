DROP TABLE IF EXISTS members CASCADE;
CREATE TABLE members (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL,
    position VARCHAR(255),
    birth_date DATE,
    address VARCHAR(255),
    phone_number VARCHAR(20)
);


