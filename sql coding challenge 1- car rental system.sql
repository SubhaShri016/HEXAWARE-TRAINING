-- create and use the database
CREATE DATABASE CarRentalSystem;
USE CarRentalSystem;

-- create the vehicle table
CREATE TABLE Vehicle (
    vehicleID INT PRIMARY KEY AUTO_INCREMENT,
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    dailyRate DECIMAL(10,2) NOT NULL,
    status ENUM('available', 'notAvailable') NOT NULL,
    passengerCapacity INT NOT NULL,
    engineCapacity INT NOT NULL
);

-- create the customer table
CREATE TABLE Customer (
    customerID INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phoneNumber VARCHAR(20) UNIQUE NOT NULL
);

-- create the lease table
CREATE TABLE Lease (
    leaseID INT PRIMARY KEY AUTO_INCREMENT,
    vehicleID INT NOT NULL,
    customerID INT NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    leaseType ENUM('Daily', 'Monthly') NOT NULL,
    FOREIGN KEY (vehicleID) REFERENCES Vehicle(vehicleID) ON DELETE CASCADE,
    FOREIGN KEY (customerID) REFERENCES Customer(customerID) ON DELETE CASCADE
);

-- create the payment table
CREATE TABLE Payment (
    paymentID INT PRIMARY KEY AUTO_INCREMENT,
    leaseID INT NOT NULL,
    paymentDate DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (leaseID) REFERENCES Lease(leaseID) ON DELETE CASCADE
);

-- Insert Sample Data into Vehicle Table
INSERT INTO Vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES
('Toyota', 'Camry', 2022, 50.00, 'available', 4, 1450),
('Honda', 'Civic', 2023, 45.00, 'available', 7, 1500),
('Ford', 'Focus', 2022, 48.00, 'notAvailable', 4, 1400),
('Nissan', 'Altima', 2023, 52.00, 'available', 7, 1200),
('Chevrolet', 'Malibu', 2022, 47.00, 'available', 4, 1800),
('Hyundai', 'Sonata', 2023, 49.00, 'notAvailable', 7, 1400),
('BMW', '3 Series', 2023, 60.00, 'available', 7, 2499),
('Mercedes', 'C-Class', 2022, 58.00, 'available', 8, 2599),
('Audi', 'A4', 2022, 55.00, 'notAvailable', 4, 2500),
('Lexus', 'ES', 2023, 54.00, 'available', 4, 2500);

-- Insert Sample Data into Customer Table
INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES
('John', 'Doe', 'johndoe@example.com', '555-555-5555'),
('Jane', 'Smith', 'janesmith@example.com', '555-123-4567'),
('Robert', 'Johnson', 'robert@example.com', '555-789-1234'),
('Sarah', 'Brown', 'sarah@example.com', '555-456-7890'),
('David', 'Lee', 'david@example.com', '555-987-6543'),
('Laura', 'Hall', 'laura@example.com', '555-234-5678'),
('Michael', 'Davis', 'michael@example.com', '555-876-5432'),
('Emma', 'Wilson', 'emma@example.com', '555-432-1098'),
('William', 'Taylor', 'william@example.com', '555-321-6547'),
('Olivia', 'Adams', 'olivia@example.com', '555-765-4321');

-- Insert Sample Data into Lease Table
INSERT INTO Lease (vehicleID, customerID, startDate, endDate, leaseType) VALUES
(1, 1, '2023-01-01', '2023-01-05', 'Daily'),
(2, 2, '2023-02-15', '2023-02-28', 'Monthly'),
(3, 3, '2023-03-10', '2023-03-15', 'Daily'),
(4, 4, '2023-04-20', '2023-04-30', 'Monthly'),
(5, 5, '2023-05-05', '2023-05-10', 'Daily'),
(4, 3, '2023-06-15', '2023-06-30', 'Monthly'),
(7, 7, '2023-07-01', '2023-07-10', 'Daily'),
(8, 8, '2023-08-12', '2023-08-15', 'Monthly'),
(3, 3, '2023-09-07', '2023-09-10', 'Daily'),
(10, 10, '2023-10-10', '2023-10-31', 'Monthly');

-- Insert Sample Data into Payment Table
INSERT INTO Payment (leaseID, paymentDate, amount) VALUES
(1, '2023-01-03', 200.00),
(2, '2023-02-20', 1000.00),
(3, '2023-03-12', 75.00),
(4, '2023-04-25', 900.00),
(5, '2023-05-07', 60.00),
(6, '2023-06-18', 1200.00),
(7, '2023-07-03', 40.00),
(8, '2023-08-14', 1100.00),
(9, '2023-09-09', 80.00),
(10, '2023-10-25', 1500.00);

-- Verify Table Structure and Data
SELECT * FROM Vehicle;
SELECT * FROM Customer;
SELECT * FROM Lease;
SELECT * FROM Payment;

-- 1. Update the daily rate for a Mercedes car to 68.
UPDATE Vehicle
SET dailyRate = 68
WHERE make = 'Mercedes';

-- 2. Delete a specific customer and all associated leases and payments. 
DELETE FROM Payment WHERE leaseID IN (SELECT leaseID FROM Lease WHERE customerID = 3);
DELETE FROM Lease WHERE customerID = 3;
DELETE FROM Customer WHERE customerID = 3;

-- 3.  Rename the "paymentDate" column in the Payment table to "transactionDate". 
ALTER TABLE Payment RENAME COLUMN paymentDate TO transactionDate;

-- 4.Find a specific customer by email. 
SELECT * FROM Customer WHERE email = 'johndoe@example.com';

-- 5. Get active leases for a specific customer. 
SELECT * FROM Lease WHERE customerID = 3 AND endDate >= CURDATE();

--  6. Find all payments made by a customer with a specific phone number.
SELECT * FROM Payment
WHERE leaseID IN (
    SELECT leaseID FROM Lease
    WHERE customerID = (SELECT customerID FROM Customer WHERE phoneNumber = '555-555-5555')
);

-- 7. Calculate the average daily rate of all available cars.
SELECT AVG(dailyRate) AS AvgDailyRate FROM Vehicle WHERE status = 'available';

-- 8. Find the car with the highest daily rate. 
SELECT * FROM Vehicle WHERE dailyRate = (SELECT MAX(dailyRate) FROM Vehicle);

-- 9. Retrieve all cars leased by a specific customer.
SELECT * FROM Vehicle
WHERE vehicleID IN (SELECT vehicleID FROM Lease WHERE customerID = 4);

-- 10. Find the details of the most recent lease. 
SELECT * FROM Lease ORDER BY startDate DESC LIMIT 1;

-- 11. List all payments made in the year 2023. 
SELECT * FROM Payment WHERE YEAR(transactionDate) = 2023;

-- 12. Retrieve customers who have not made any payments.
SELECT * FROM Customer
WHERE customerID NOT IN (SELECT DISTINCT customerID FROM Lease WHERE leaseID IN (SELECT leaseID FROM Payment));

-- 13. Retrieve Car Details and Their Total Payments. 
SELECT V.vehicleID, V.make, V.model, SUM(P.amount) AS TotalPayments
FROM Vehicle V
JOIN Lease L ON V.vehicleID = L.vehicleID
JOIN Payment P ON L.leaseID = P.leaseID
GROUP BY V.vehicleID, V.make, V.model;

-- 14. Calculate Total Payments for Each Customer. 
SELECT C.customerID, C.firstName, C.lastName, SUM(P.amount) AS TotalSpent
FROM Customer C
JOIN Lease L ON C.customerID = L.customerID
JOIN Payment P ON L.leaseID = P.leaseID
GROUP BY C.customerID, C.firstName, C.lastName;

-- 15. List Car Details for Each Lease. 
SELECT L.leaseID, C.firstName, C.lastName, V.make, V.model, L.startDate, L.endDate
FROM Lease L
JOIN Customer C ON L.customerID = C.customerID
JOIN Vehicle V ON L.vehicleID = V.vehicleID;

-- 16. Retrieve Details of Active Leases with Customer and Car Information.
SELECT L.leaseID, C.firstName, C.lastName, V.make, V.model, L.startDate, L.endDate
FROM Lease L
JOIN Customer C ON L.customerID = C.customerID
JOIN Vehicle V ON L.vehicleID = V.vehicleID
WHERE L.endDate >= CURDATE();

-- 17. Find the Customer Who Has Spent the Most on Leases.
SELECT C.customerID, C.firstName, C.lastName, SUM(P.amount) AS TotalSpent
FROM Customer C
JOIN Lease L ON C.customerID = L.customerID
JOIN Payment P ON L.leaseID = P.leaseID
GROUP BY C.customerID, C.firstName, C.lastName
ORDER BY TotalSpent DESC
LIMIT 1;

-- 18. List All Cars with Their Current Lease Information.
SELECT V.vehicleID, V.make, V.model, L.leaseID, C.firstName, C.lastName, L.startDate, L.endDate
FROM Vehicle V
LEFT JOIN Lease L ON V.vehicleID = L.vehicleID
LEFT JOIN Customer C ON L.customerID = C.customerID;




















