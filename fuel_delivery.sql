CREATE DATABASE fuel_delivery;
USE fuel_delivery;

CREATE TABLE drivers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    is_available BOOLEAN
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone_number VARCHAR(20),
    location VARCHAR(100),
    fuel_type VARCHAR(50),
    payment_amount DOUBLE
);
