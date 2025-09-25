CREATE DATABASE IF NOT EXISTS inventorydb;
USE inventorydb;
CREATE TABLE IF NOT EXISTS products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO products(name, quantity, price) VALUES
('Mouse', 12, 14.99),
('Keyboard', 5, 29.99),
('Monitor', 3, 189.00);
