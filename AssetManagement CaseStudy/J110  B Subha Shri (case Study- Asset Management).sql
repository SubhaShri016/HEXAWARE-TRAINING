CREATE DATABASE DigitalAssetManagement;
USE DigitalAssetManagement;

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE assets (
    asset_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    serial_number VARCHAR(50) UNIQUE NOT NULL,
    purchase_date DATE NOT NULL,
    location VARCHAR(100) NOT NULL,
    status ENUM('in use', 'decommissioned', 'under maintenance') NOT NULL,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES employees(employee_id) ON DELETE SET NULL
);

CREATE TABLE maintenance_records (
    maintenance_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    maintenance_date DATE NOT NULL,
    description TEXT NOT NULL,
    cost DECIMAL(10,2) NOT NULL CHECK (cost >= 0),
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE
);

CREATE TABLE asset_allocations (
    allocation_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    employee_id INT NOT NULL,
    allocation_date DATE NOT NULL,
    return_date DATE DEFAULT NULL,
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    asset_id INT NOT NULL,
    employee_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('pending', 'approved', 'canceled') NOT NULL DEFAULT 'pending',
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

INSERT INTO employees (name, department, email, password) VALUES
('John Doe', 'IT', 'john.doe@example.com', 'hashedpassword1'),
('Jane Smith', 'HR', 'jane.smith@example.com', 'hashedpassword2'),
('Alice Johnson', 'Finance', 'alice.johnson@example.com', 'hashedpassword3'),
('Bob Williams', 'Operations', 'bob.williams@example.com', 'hashedpassword4'),
('Emily Davis', 'Marketing', 'emily.davis@example.com', 'hashedpassword5'),
('Michael Brown', 'Sales', 'michael.brown@example.com', 'hashedpassword6'),
('Sophia Wilson', 'Engineering', 'sophia.wilson@example.com', 'hashedpassword7'),
('Liam Martinez', 'Legal', 'liam.martinez@example.com', 'hashedpassword8'),
('Olivia Taylor', 'Procurement', 'olivia.taylor@example.com', 'hashedpassword9'),
('William Anderson', 'Support', 'william.anderson@example.com', 'hashedpassword10');

INSERT INTO assets (name, type, serial_number, purchase_date, location, status, owner_id) VALUES
('Dell Laptop', 'Laptop', 'SN12345', '2023-01-15', 'Office A', 'in use', 1),
('Toyota Car', 'Vehicle', 'VIN67890', '2022-05-10', 'Garage', 'in use', 2),
('Canon Printer', 'Equipment', 'PRT001', '2021-09-20', 'Office B', 'under maintenance', 3),
('Lenovo Laptop', 'Laptop', 'SN56789', '2023-03-05', 'Office C', 'in use', 4),
('iMac', 'Computer', 'IMAC2023', '2023-06-10', 'Office A', 'in use', 5),
('HP LaserJet', 'Printer', 'HP56789', '2022-07-15', 'Office D', 'under maintenance', 6),
('Tesla Model 3', 'Vehicle', 'TESLA123', '2023-02-20', 'Garage', 'in use', 7),
('Cisco Router', 'Networking', 'CISCO987', '2023-04-12', 'Server Room', 'in use', 8),
('Projector', 'Equipment', 'PROJ456', '2023-08-05', 'Conference Hall', 'in use', 9),
('External HDD', 'Storage', 'HDD876', '2023-09-15', 'IT Storage', 'in use', 10);

INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES
(3, '2024-02-10', 'Replaced ink cartridges and cleaned rollers', 50.00),
(2, '2024-03-05', 'Oil change and tire replacement', 300.00),
(6, '2024-01-12', 'Fixed paper jam and replaced toner', 80.00),
(1, '2023-12-20', 'Replaced laptop battery', 120.00),
(7, '2024-04-05', 'Battery replacement', 500.00),
(5, '2024-03-15', 'MacOS upgrade and disk cleanup', 150.00),
(8, '2024-02-25', 'Firmware update and security patching', 75.00),
(9, '2024-01-30', 'Lens replacement', 200.00),
(10, '2024-05-01', 'Data recovery and backup setup', 95.00),
(4, '2024-04-10', 'Screen replacement', 180.00);

INSERT INTO asset_allocations (asset_id, employee_id, allocation_date, return_date) VALUES
(1, 1, '2024-01-10', NULL),
(2, 2, '2024-02-15', '2024-03-01'),
(3, 3, '2024-03-20', NULL),
(4, 4, '2024-04-05', NULL),
(5, 5, '2024-02-10', '2024-03-20'),
(6, 6, '2024-01-15', NULL),
(7, 7, '2024-05-01', NULL),
(8, 8, '2024-03-10', '2024-04-15'),
(9, 9, '2024-06-05', NULL),
(10, 10, '2024-04-25', NULL);

INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES
(4, 3, '2024-03-01', '2024-03-10', '2024-03-20', 'approved'),
(3, 4, '2024-03-02', '2024-03-15', '2024-03-30', 'pending'),
(5, 1, '2024-02-10', '2024-02-20', '2024-02-28', 'approved'),
(6, 2, '2024-04-05', '2024-04-15', '2024-04-25', 'canceled'),
(7, 3, '2024-05-12', '2024-05-18', '2024-05-28', 'pending'),
(8, 5, '2024-02-08', '2024-02-12', '2024-02-22', 'approved'),
(9, 6, '2024-01-25', '2024-02-01', '2024-02-10', 'canceled'),
(10, 7, '2024-06-01', '2024-06-05', '2024-06-15', 'pending'),
(1, 8, '2024-07-10', '2024-07-15', '2024-07-25', 'approved'),
(2, 9, '2024-08-05', '2024-08-10', '2024-08-20', 'pending');

SELECT * FROM employees;
SELECT * FROM assets;
SELECT * FROM maintenance_records;
SELECT * FROM asset_allocations;
SELECT * FROM reservations;



