CREATE DATABASE HMBank;
USE HMBank;

CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    DOB DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE Accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    account_type ENUM('savings', 'current', 'zero_balance') NOT NULL,
    balance DECIMAL(15,2) NOT NULL CHECK (balance >= 0),
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    transaction_type ENUM('deposit', 'withdrawal', 'transfer') NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount > 0),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
);

INSERT INTO Customers (first_name, last_name, DOB, email, phone_number, address) VALUES
('John', 'Doe', '1990-05-15', 'john.doe@email.com', '9876543210', '123 Main St, CityA'),
('Alice', 'Smith', '1985-08-25', 'alice.smith@email.com', '8765432109', '456 Oak St, CityB'),
('Bob', 'Johnson', '1978-11-12', 'bob.johnson@email.com', '7654321098', '789 Pine St, CityC'),
('Emma', 'Brown', '1995-03-30', 'emma.brown@email.com', '6543210987', '321 Cedar St, CityD'),
('Liam', 'Davis', '1980-06-18', 'liam.davis@email.com', '5432109876', '654 Maple St, CityE'),
('Sophia', 'Miller', '1992-07-22', 'sophia.miller@email.com', '4321098765', '987 Birch St, CityF'),
('James', 'Wilson', '1975-12-01', 'james.wilson@email.com', '3210987654', '741 Elm St, CityG'),
('Olivia', 'Martinez', '1998-09-15', 'olivia.martinez@email.com', '2109876543', '852 Walnut St, CityH'),
('Ethan', 'Taylor', '1989-04-27', 'ethan.taylor@email.com', '1098765432', '369 Redwood St, CityI'),
('Ava', 'Anderson', '1993-10-05', 'ava.anderson@email.com', '9988776655', '951 Spruce St, CityJ');

INSERT INTO Accounts (customer_id, account_type, balance) VALUES
(1, 'savings', 5000.00),
(2, 'current', 12000.00),
(3, 'zero_balance', 0.00),
(4, 'savings', 3000.00),
(5, 'current', 15000.00),
(6, 'savings', 7000.00),
(7, 'zero_balance', 0.00),
(8, 'current', 18000.00),
(9, 'savings', 9000.00),
(10, 'current', 20000.00);

INSERT INTO Transactions (account_id, transaction_type, amount) VALUES
(1, 'deposit', 2000.00),
(2, 'withdrawal', 500.00),
(3, 'deposit', 1000.00),
(4, 'deposit', 2500.00),
(5, 'withdrawal', 2000.00),
(6, 'deposit', 3000.00),
(7, 'deposit', 500.00),
(8, 'withdrawal', 1500.00),
(9, 'deposit', 1000.00),
(10, 'withdrawal', 2500.00);

SELECT c.first_name, c.last_name, c.email, a.account_type
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id;

SELECT t.transaction_id, c.first_name, c.last_name, t.transaction_type, t.amount, t.transaction_date
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
JOIN Customers c ON a.customer_id = c.customer_id;

UPDATE Accounts SET balance = balance + 500 WHERE account_id = 1;

SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM Customers;

DELETE FROM Accounts WHERE balance = 0 AND account_type = 'savings';

SELECT * FROM Customers WHERE address LIKE '%CityA%';

SELECT * FROM Accounts WHERE account_type = 'current' AND balance > 1000;

SELECT * FROM Transactions WHERE account_id = 1;

SELECT account_id, balance, balance * 0.045 AS interest_accrued
FROM Accounts WHERE account_type = 'savings';

SELECT * FROM Accounts WHERE balance < 500;

SELECT * FROM Customers WHERE address NOT LIKE '%CityA%';

SELECT AVG(balance) AS avg_balance FROM Accounts;

SELECT * FROM Accounts ORDER BY balance DESC LIMIT 10;

SELECT SUM(amount) AS total_deposits FROM Transactions 
WHERE transaction_type = 'deposit' AND DATE(transaction_date) = '2024-03-10';

SELECT * FROM Customers ORDER BY DOB ASC LIMIT 1; -- Oldest
SELECT * FROM Customers ORDER BY DOB DESC LIMIT 1; -- Newest

SELECT t.transaction_id, t.transaction_type, t.amount, a.account_type
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id;

SELECT c.customer_id, c.first_name, c.last_name, a.account_id, a.account_type, a.balance
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id;

SELECT t.transaction_id, t.transaction_type, t.amount, t.transaction_date, 
       c.first_name, c.last_name, c.email
FROM Transactions t
JOIN Accounts a ON t.account_id = a.account_id
JOIN Customers c ON a.customer_id = c.customer_id
WHERE t.account_id = 1;

SELECT c.customer_id, c.first_name, c.last_name, COUNT(a.account_id) AS account_count
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id
HAVING COUNT(a.account_id) > 1;

SELECT account_id, 
       SUM(CASE WHEN transaction_type = 'deposit' THEN amount ELSE 0 END) AS total_deposits,
       SUM(CASE WHEN transaction_type = 'withdrawal' THEN amount ELSE 0 END) AS total_withdrawals,
       (SUM(CASE WHEN transaction_type = 'deposit' THEN amount ELSE 0 END) - 
        SUM(CASE WHEN transaction_type = 'withdrawal' THEN amount ELSE 0 END)) AS net_balance_change
FROM Transactions
GROUP BY account_id;

SELECT account_id, AVG(balance) AS avg_daily_balance
FROM Accounts
WHERE account_id IN (SELECT DISTINCT account_id FROM Transactions WHERE transaction_date BETWEEN '2024-01-01' AND '2024-12-31')
GROUP BY account_id;

SELECT account_type, SUM(balance) AS total_balance
FROM Accounts
GROUP BY account_type;

SELECT account_id, COUNT(transaction_id) AS transaction_count
FROM Transactions
GROUP BY account_id
ORDER BY transaction_count DESC;

SELECT c.customer_id, c.first_name, c.last_name, a.account_type, SUM(a.balance) AS total_balance
FROM Customers c
JOIN Accounts a ON c.customer_id = a.customer_id
GROUP BY c.customer_id, a.account_type
HAVING total_balance > 10000; -- Adjust this threshold as needed

SELECT account_id, amount, transaction_date, COUNT(*) AS duplicate_count
FROM Transactions
GROUP BY account_id, amount, transaction_date
HAVING COUNT(*) > 1;

SELECT * FROM Customers 
WHERE customer_id = (SELECT customer_id FROM Accounts ORDER BY balance DESC LIMIT 1);

SELECT AVG(balance) AS avg_balance
FROM Accounts
WHERE customer_id IN (SELECT customer_id FROM Accounts GROUP BY customer_id HAVING COUNT(account_id) > 1);

SELECT * FROM Transactions 
WHERE amount > (SELECT AVG(amount) FROM Transactions);

SELECT * FROM Customers 
WHERE customer_id NOT IN (SELECT DISTINCT customer_id FROM Accounts WHERE account_id IN (SELECT DISTINCT account_id FROM Transactions));

SELECT SUM(balance) AS total_balance
FROM Accounts
WHERE account_id NOT IN (SELECT DISTINCT account_id FROM Transactions);

SELECT * FROM Transactions 
WHERE account_id IN (SELECT account_id FROM Accounts ORDER BY balance ASC LIMIT 1);

SELECT customer_id, COUNT(DISTINCT account_type) AS account_types_count
FROM Accounts
GROUP BY customer_id
HAVING COUNT(DISTINCT account_type) > 1;

SELECT account_type, 
       (COUNT(account_id) * 100.0 / (SELECT COUNT(*) FROM Accounts)) AS percentage
FROM Accounts
GROUP BY account_type;

SELECT * FROM Transactions 
WHERE account_id IN (SELECT account_id FROM Accounts WHERE customer_id = 1);

SELECT account_type, 
       (SELECT SUM(balance) FROM Accounts a2 WHERE a1.account_type = a2.account_type) AS total_balance
FROM Accounts a1
GROUP BY account_type;








