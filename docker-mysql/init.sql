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
