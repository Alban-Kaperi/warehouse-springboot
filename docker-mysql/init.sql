CREATE DATABASE IF NOT EXISTS inventory;
USE inventory;

-- Create 'roles' table if it doesn't exist
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- I know that I could have used also INT because roles table generally doesn't hold many records.
    name VARCHAR(100) UNIQUE NOT NULL
);

-- Insert default roles if they don't exist
INSERT INTO roles (name)
SELECT * FROM (SELECT 'CLIENT') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'CLIENT');

INSERT INTO roles (name)
SELECT * FROM (SELECT 'WAREHOUSE_MANAGER') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'WAREHOUSE_MANAGER');

INSERT INTO roles (name)
SELECT * FROM (SELECT 'SYSTEM_ADMIN') AS tmp
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'SYSTEM_ADMIN');

-- Create the 'items' table if it doesn't exist
CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price DOUBLE NOT NULL DEFAULT 0
);

-- Insert sample data into the 'items' table
INSERT INTO items (name, quantity, price)
VALUES
    ('Cement - Type I', 500, 7.50),
    ('Steel Rebar - 10mm', 125.75, 2.25),
    ('Bricks - Red Clay', 10000, 0.35),
    ('Grout - Non-Shrink (50lb bag)', 25, 18.99),
    ('Tiles - Ceramic - White', 5000, 1.50),
    ('Labor - Demolition', 40, 50.00),
    ('2x4 Lumber - 8ft', 200, 4.50)
    ON DUPLICATE KEY UPDATE
         quantity = VALUES(quantity),
         price = VALUES(price); -- Ensures no duplicate entries for items with the same name