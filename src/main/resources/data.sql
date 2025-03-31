-- Drop Tables (in reverse order to maintain integrity)
DROP TABLE IF EXISTS users;

-- Create Users Table
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(15),
    role ENUM('ADMIN', 'USER') DEFAULT 'USER' NOT NULL
);


-- Insert Sample Data into Users
INSERT INTO users (name, email, password, address, phone_number, role) VALUES
('Admin User', 'admin@ecommerce.com', 'admin123', '123 Admin St', '1234567890', 'ADMIN'),
('John Doe', 'john@ecommerce.com', 'john123', '456 Main St', '9876543210', 'USER'),
('Jane Smith', 'jane@ecommerce.com', 'jane123', '789 Elm St', '5551234567', 'USER');

