-- Crear la base de datos (si aún no existe)
CREATE DATABASE IF NOT EXISTS inventariodb;

-- Usar la base de datos creada
USE inventariodb;

-- Crear la tabla de products
CREATE TABLE IF NOT EXISTS Productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL
);

-- Insertar ejemplos de products
INSERT INTO
    Productos (nombre, cantidad, precio)
VALUES ('Laptop DELL', 155, 21081.99),
    ('TV SAMSUMG', 15, 4929.00),
    ('PHONE 14', 20, 24432.00);