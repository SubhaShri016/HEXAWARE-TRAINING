-- Task 1: Database Design:

CREATE DATABASE TechShop;
USE TechShop;

CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Phone VARCHAR(15) UNIQUE NOT NULL,
    Address TEXT NOT NULL
);

CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    Description TEXT,
    Price DECIMAL(10,2) NOT NULL CHECK (Price >= 0)
);

CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATE NOT NULL,
    TotalAmount DECIMAL(10,2) NOT NULL CHECK (TotalAmount >= 0),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE
);

CREATE TABLE OrderDetails (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT,
    ProductID INT,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);

CREATE TABLE Inventory (
    InventoryID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT,
    QuantityInStock INT NOT NULL CHECK (QuantityInStock >= 0),
    LastStockUpdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE CASCADE
);

INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES
('John', 'Doe', 'john.doe@email.com', '1234567890', '123 Main St, CityA'),
('Alice', 'Smith', 'alice.smith@email.com', '9876543210', '456 Oak St, CityB'),
('Bob', 'Johnson', 'bob.johnson@email.com', '5678901234', '789 Pine St, CityC'),
('Emma', 'Brown', 'emma.brown@email.com', '6789012345', '321 Cedar St, CityD'),
('Liam', 'Davis', 'liam.davis@email.com', '7890123456', '654 Maple St, CityE'),
('Sophia', 'Miller', 'sophia.miller@email.com', '8901234567', '987 Birch St, CityF'),
('James', 'Wilson', 'james.wilson@email.com', '9012345678', '741 Elm St, CityG'),
('Olivia', 'Martinez', 'olivia.martinez@email.com', '1122334455', '852 Walnut St, CityH'),
('Ethan', 'Taylor', 'ethan.taylor@email.com', '2233445566', '369 Redwood St, CityI'),
('Ava', 'Anderson', 'ava.anderson@email.com', '3344556677', '951 Spruce St, CityJ');

INSERT INTO Products (ProductName, Description, Price) VALUES
('Smartphone', 'Latest model with 6.5-inch display', 699.99),
('Laptop', 'Powerful gaming laptop with 16GB RAM', 1299.99),
('Headphones', 'Noise-cancelling wireless headphones', 199.99),
('Smartwatch', 'Fitness tracker with heart rate monitor', 149.99),
('Tablet', '10-inch tablet with 64GB storage', 399.99),
('Monitor', '27-inch 4K UHD display', 349.99),
('Keyboard', 'Mechanical keyboard with RGB lighting', 99.99),
('Mouse', 'Wireless ergonomic mouse', 49.99),
('External HDD', '1TB external hard drive', 79.99),
('Charger', 'Fast charging adapter', 29.99);

INSERT INTO Orders (CustomerID, OrderDate, TotalAmount) VALUES
(1, '2024-03-01', 749.98),
(2, '2024-03-02', 399.99),
(3, '2024-03-05', 1299.99),
(4, '2024-03-07', 699.99),
(5, '2024-03-10', 249.98),
(6, '2024-03-12', 499.99),
(7, '2024-03-15', 199.99),
(8, '2024-03-18', 149.99),
(9, '2024-03-20', 349.99),
(10, '2024-03-22', 29.99);

INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES
(1, 1, 1),
(1, 10, 1),
(2, 5, 1),
(3, 2, 1),
(4, 1, 1),
(5, 3, 1),
(5, 4, 1),
(6, 6, 1),
(7, 3, 1),
(8, 4, 1);

INSERT INTO Inventory (ProductID, QuantityInStock) VALUES
(1, 50),
(2, 30),
(3, 100),
(4, 70),
(5, 40),
(6, 20),
(7, 60),
(8, 90),
(9, 80),
(10, 150);

-- Tasks 2: Select, Where, Between, AND, LIKE

SELECT FirstName, LastName, Email FROM Customers;

SELECT Orders.OrderID, Orders.OrderDate, Customers.FirstName, Customers.LastName
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID;

INSERT INTO Customers (FirstName, LastName, Email, Phone, Address)
VALUES ('John', 'Doe', 'johndoe@example.com', '1234567890', '123 Tech Street');

UPDATE Products
SET Price = Price * 1.10;

DELETE FROM OrderDetails WHERE OrderID = ?;
DELETE FROM Orders WHERE OrderID = ?;

INSERT INTO Orders (CustomerID, OrderDate, TotalAmount)
VALUES (?, CURDATE(), 0);  -- Placeholder, TotalAmount will be updated later

UPDATE Customers
SET Email = ?, Address = ?
WHERE CustomerID = ?;

UPDATE Orders
SET TotalAmount = (
    SELECT SUM(Products.Price * OrderDetails.Quantity)
    FROM OrderDetails
    JOIN Products ON OrderDetails.ProductID = Products.ProductID
    WHERE OrderDetails.OrderID = Orders.OrderID
);

DELETE FROM OrderDetails WHERE OrderID IN (SELECT OrderID FROM Orders WHERE CustomerID = ?);
DELETE FROM Orders WHERE CustomerID = ?;

INSERT INTO Products (ProductName, Description, Price)
VALUES ('Smartphone XYZ', 'Latest smartphone with AI features', 699.99);

ALTER TABLE Orders ADD COLUMN Status VARCHAR(20) DEFAULT 'Pending';
UPDATE Orders SET Status = ? WHERE OrderID = ?;

ALTER TABLE Customers ADD COLUMN OrderCount INT DEFAULT 0;
UPDATE Customers
SET OrderCount = (
    SELECT COUNT(*) FROM Orders WHERE Orders.CustomerID = Customers.CustomerID
);

-- Tasks 2: Select, Where, Between, AND, LIKE

SELECT Orders.OrderID, Orders.OrderDate, Customers.FirstName, Customers.LastName
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID;

SELECT Orders.OrderID, Orders.OrderDate, Customers.FirstName, Customers.LastName
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID;

SELECT Products.ProductName, SUM(Products.Price * OrderDetails.Quantity) AS TotalRevenue
FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID
GROUP BY Products.ProductID;

SELECT DISTINCT Customers.FirstName, Customers.LastName, Customers.Email
FROM Customers
JOIN Orders ON Customers.CustomerID = Orders.CustomerID;

SELECT Products.ProductName, SUM(OrderDetails.Quantity) AS TotalQuantityOrdered
FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID
GROUP BY Products.ProductID
ORDER BY TotalQuantityOrdered DESC
LIMIT 1;

SELECT ProductName, Category FROM Products;

SELECT Customers.FirstName, Customers.LastName, AVG(Orders.TotalAmount) AS AvgOrderValue
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID
GROUP BY Customers.CustomerID;

SELECT Orders.OrderID, Customers.FirstName, Customers.LastName, Orders.TotalAmount
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID
ORDER BY Orders.TotalAmount DESC
LIMIT 1;

-- Task 4: Subquery and its type:

SELECT * FROM Customers
WHERE CustomerID NOT IN (SELECT DISTINCT CustomerID FROM Orders);

SELECT COUNT(*) AS TotalProducts FROM Products;

SELECT SUM(TotalAmount) AS TotalRevenue FROM Orders;

SELECT AVG(OrderDetails.Quantity) AS AvgQuantity
FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID
WHERE Products.Category = ?;

SELECT SUM(Orders.TotalAmount) AS CustomerRevenue
FROM Orders
WHERE CustomerID = ?;

SELECT Customers.FirstName, Customers.LastName, COUNT(Orders.OrderID) AS OrderCount
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID
GROUP BY Customers.CustomerID
ORDER BY OrderCount DESC
LIMIT 1;

SELECT Products.Category, SUM(OrderDetails.Quantity) AS TotalQuantity
FROM OrderDetails
JOIN Products ON OrderDetails.ProductID = Products.ProductID
GROUP BY Products.Category
ORDER BY TotalQuantity DESC
LIMIT 1;

SELECT Customers.FirstName, Customers.LastName, SUM(Orders.TotalAmount) AS TotalSpent
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID
GROUP BY Customers.CustomerID
ORDER BY TotalSpent DESC
LIMIT 1;

SELECT AVG(TotalAmount) AS AvgOrderValue FROM Orders;

SELECT Customers.FirstName, Customers.LastName, COUNT(Orders.OrderID) AS OrderCount
FROM Orders
JOIN Customers ON Orders.CustomerID = Customers.CustomerID
GROUP BY Customers.CustomerID;


