-- Tasks 1: Database Design:  
 
CREATE DATABASE TicketBookingSystem;
USE TicketBookingSystem;

CREATE TABLE Venue (
    venue_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE Event (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    event_time TIME NOT NULL,
    venue_id INT,
    total_seats INT NOT NULL CHECK (total_seats > 0),
    available_seats INT NOT NULL CHECK (available_seats >= 0),
    ticket_price DECIMAL(10,2) NOT NULL CHECK (ticket_price > 0),
    event_type ENUM('Movie', 'Sports', 'Concert') NOT NULL,
    FOREIGN KEY (venue_id) REFERENCES Venue(venue_id) ON DELETE CASCADE
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE Booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    event_id INT NOT NULL,
    num_tickets INT NOT NULL CHECK (num_tickets > 0),
    total_cost DECIMAL(10,2) NOT NULL CHECK (total_cost > 0),
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Event(event_id) ON DELETE CASCADE
);

-- Tasks 2: Select, Where, Between, AND, LIKE: 

-- 1. Insert 10 Sample Records into Each Table

INSERT INTO Venue (venue_name, address) VALUES
('Stadium A', '123 Main St'),
('Theater B', '456 Elm St'),
('Concert Hall C', '789 Oak St'),
('Sports Arena D', '321 Pine St'),
('Movie Plex E', '654 Maple St'),
('Arena F', '987 Cedar St'),
('Concert Dome G', '741 Birch St'),
('Grand Hall H', '852 Walnut St'),
('Theater I', '369 Redwood St'),
('Event Center J', '951 Spruce St');

INSERT INTO Event (event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type) VALUES
('Rock Concert', '2024-06-15', '19:30:00', 1, 20000, 15000, 2500.00, 'Concert'),
('Football Match', '2024-07-20', '18:00:00', 2, 30000, 25000, 1500.00, 'Sports'),
('Movie Premiere', '2024-06-10', '21:00:00', 3, 500, 100, 800.00, 'Movie'),
('Comedy Show', '2024-05-25', '20:00:00', 4, 1000, 300, 1200.00, 'Concert'),
('Tennis Open', '2024-08-05', '16:30:00', 5, 15000, 8000, 1800.00, 'Sports'),
('Jazz Night', '2024-06-20', '22:00:00', 6, 800, 200, 900.00, 'Concert'),
('Basketball Game', '2024-07-30', '19:00:00', 7, 20000, 12000, 2000.00, 'Sports'),
('Drama Play', '2024-05-15', '18:45:00', 8, 600, 150, 950.00, 'Movie'),
('Metal Festival', '2024-09-10', '20:30:00', 9, 15000, 7000, 2200.00, 'Concert'),
('Soccer Final', '2024-08-18', '17:30:00', 10, 35000, 27000, 1700.00, 'Sports');

INSERT INTO Customer (customer_name, email, phone_number) VALUES
('John Doe', 'john.doe@email.com', '9876543210'),
('Alice Smith', 'alice.smith@email.com', '8765432109'),
('Bob Johnson', 'bob.johnson@email.com', '7654321098'),
('Emma Brown', 'emma.brown@email.com', '6543210987'),
('Liam Davis', 'liam.davis@email.com', '5432109876'),
('Sophia Miller', 'sophia.miller@email.com', '4321098765'),
('James Wilson', 'james.wilson@email.com', '3210987654'),
('Olivia Martinez', 'olivia.martinez@email.com', '2109876543'),
('Ethan Taylor', 'ethan.taylor@email.com', '1098765432'),
('Ava Anderson', 'ava.anderson@email.com', '9988776655');

INSERT INTO Booking (customer_id, event_id, num_tickets, total_cost) VALUES
(1, 1, 5, 12500.00),
(2, 2, 3, 4500.00),
(3, 3, 2, 1600.00),
(4, 4, 4, 4800.00),
(5, 5, 1, 1800.00),
(6, 6, 6, 5400.00),
(7, 7, 8, 16000.00),
(8, 8, 2, 1900.00),
(9, 9, 10, 22000.00),
(10, 10, 7, 11900.00);

-- 2. Retrieve All Events
SELECT * FROM Event;

-- 3. Select Events with Available Tickets
SELECT * FROM Event WHERE available_seats > 0;

-- 3. Select Events with Available Tickets
SELECT * FROM Event WHERE event_name LIKE '%cup%';

-- 5. Select Events with Ticket Price Between 1000 and 2500
SELECT * FROM Event WHERE ticket_price BETWEEN 1000 AND 2500;

-- 6. Retrieve Events with Dates in a Specific Range
SELECT * FROM Event WHERE event_date BETWEEN '2024-06-01' AND '2024-08-01';

-- 7. Retrieve Available Events That Are Concerts
SELECT * FROM Event WHERE available_seats > 0 AND event_type = 'Concert';

-- 8. Retrieve Users in Batches of 5, Starting from 6th User
SELECT * FROM Customer LIMIT 5 OFFSET 5;

-- 9. Retrieve Bookings Where More Than 4 Tickets Were Booked
SELECT * FROM Booking WHERE num_tickets > 4;

-- 10. Retrieve Customers Whose Phone Number Ends with ‘000’
SELECT * FROM Customer WHERE phone_number LIKE '%000';

-- 11. Retrieve Events Ordered by Seat Capacity More Than 15000
SELECT * FROM Event WHERE total_seats > 15000 ORDER BY total_seats DESC;

-- 12. Select Events Name Not Starting with ‘X’, ‘Y’, ‘Z’
SELECT * FROM Event WHERE event_name NOT LIKE 'X%' AND event_name NOT LIKE 'Y%' AND event_name NOT LIKE 'Z%';

-- Tasks 3: Aggregate functions, Having, Order By, GroupBy and Joins:

-- 1. List Events and Their Average Ticket Prices
SELECT event_type, AVG(ticket_price) AS avg_ticket_price 
FROM Event 
GROUP BY event_type;

-- 2. Calculate the Total Revenue Generated by Events
SELECT e.event_name, SUM(b.total_cost) AS total_revenue
FROM Booking b
JOIN Event e ON b.event_id = e.event_id
GROUP BY e.event_name;

-- 3. Find the Event with the Highest Ticket Sales
SELECT e.event_name, SUM(b.num_tickets) AS total_tickets_sold
FROM Booking b
JOIN Event e ON b.event_id = e.event_id
GROUP BY e.event_name
ORDER BY total_tickets_sold DESC
LIMIT 1;

-- 4. Calculate the Total Number of Tickets Sold for Each Event
SELECT e.event_name, SUM(b.num_tickets) AS total_tickets_sold
FROM Booking b
JOIN Event e ON b.event_id = e.event_id
GROUP BY e.event_name;

--  5. Find Events with No Ticket Sales
SELECT event_name 
FROM Event 
WHERE event_id NOT IN (SELECT DISTINCT event_id FROM Booking);

-- 6. Find the User Who Has Booked the Most Tickets
SELECT c.customer_name, SUM(b.num_tickets) AS total_tickets
FROM Booking b
JOIN Customer c ON b.customer_id = c.customer_id
GROUP BY c.customer_id
ORDER BY total_tickets DESC
LIMIT 1;

-- 7. List Events and the Total Number of Tickets Sold for Each Month
SELECT DATE_FORMAT(e.event_date, '%Y-%m') AS month, e.event_name, SUM(b.num_tickets) AS total_tickets
FROM Booking b
JOIN Event e ON b.event_id = e.event_id
GROUP BY month, e.event_name;

-- 8. Calculate the Average Ticket Price for Events in Each Venue
SELECT v.venue_name, AVG(e.ticket_price) AS avg_ticket_price
FROM Event e
JOIN Venue v ON e.venue_id = v.venue_id
GROUP BY v.venue_name;

-- 9. Calculate the Total Number of Tickets Sold for Each Event Type
SELECT event_type, SUM(b.num_tickets) AS total_tickets
FROM Booking b
JOIN Event e ON b.event_id = e.event_id
GROUP BY e.event_type;

-- 10. Calculate the Total Revenue Generated by Events in Each Year
SELECT YEAR(e.event_date) AS event_year, SUM(b.total_cost) AS total_revenue
FROM Booking b
JOIN Event e ON b.event_id = e.event_id
GROUP BY event_year;

-- 11. List Users Who Have Booked Tickets for Multiple Events
SELECT c.customer_id, c.customer_name, COUNT(DISTINCT b.event_id) AS event_count
FROM Booking b
JOIN Customer c ON b.customer_id = c.customer_id
GROUP BY c.customer_id
HAVING event_count > 1;

-- 12. Calculate the Total Revenue Generated by Events for Each User
SELECT c.customer_name, SUM(b.total_cost) AS total_spent
FROM Booking b
JOIN Customer c ON b.customer_id = c.customer_id
GROUP BY c.customer_id;

-- 13. Calculate the Average Ticket Price for Events in Each Category and Venue
SELECT e.event_type, v.venue_name, AVG(e.ticket_price) AS avg_price
FROM Event e
JOIN Venue v ON e.venue_id = v.venue_id
GROUP BY e.event_type, v.venue_name;

-- 14. List Users and the Total Number of Tickets They've Purchased in the Last 30 Days
SELECT c.customer_name, SUM(b.num_tickets) AS total_tickets
FROM Booking b
JOIN Customer c ON b.customer_id = c.customer_id
WHERE b.booking_date >= CURDATE() - INTERVAL 30 DAY
GROUP BY c.customer_id;

-- Tasks 4: Subquery and its types

-- 1. Calculate the Average Ticket Price for Events in Each Venue Using a Subquery
SELECT venue_name, 
       (SELECT AVG(ticket_price) FROM Event WHERE Venue.venue_id = Event.venue_id) AS avg_ticket_price
FROM Venue;

-- 2. Find Events with More Than 50% of Tickets Sold Using a Subquery
SELECT event_name 
FROM Event 
WHERE total_seats / 2 < (SELECT SUM(num_tickets) FROM Booking WHERE Booking.event_id = Event.event_id);

-- 3. Calculate the Total Number of Tickets Sold for Each Event
SELECT event_name, 
       (SELECT SUM(num_tickets) FROM Booking WHERE Booking.event_id = Event.event_id) AS total_tickets_sold
FROM Event;

-- 4. Find Users Who Have Not Booked Any Tickets Using a NOT EXISTS Subquery
SELECT * FROM Customer c
WHERE NOT EXISTS (SELECT 1 FROM Booking b WHERE b.customer_id = c.customer_id);

-- 5. List Events with No Ticket Sales Using a NOT IN Subquery
SELECT event_name FROM Event 
WHERE event_id NOT IN (SELECT DISTINCT event_id FROM Booking);

-- 6. Calculate the Total Number of Tickets Sold for Each Event Type Using a Subquery in the FROM Clause
SELECT event_type, SUM(total_tickets) AS total_tickets_sold
FROM (
    SELECT event_type, num_tickets AS total_tickets
    FROM Event e
    JOIN Booking b ON e.event_id = b.event_id
) AS temp
GROUP BY event_type;

-- 7. Find Events with Ticket Prices Higher Than the Average Ticket Price Using a Subquery in the WHERE Clause
SELECT * FROM Event 
WHERE ticket_price > (SELECT AVG(ticket_price) FROM Event);

-- 8. Calculate the Total Revenue Generated by Events for Each User Using a Correlated Subquery
SELECT customer_name, 
       (SELECT SUM(total_cost) FROM Booking WHERE Booking.customer_id = Customer.customer_id) AS total_spent
FROM Customer;

-- 9. List Users Who Have Booked Tickets for Events in a Given Venue Using a Subquery in the WHERE Clause
SELECT * FROM Customer 
WHERE customer_id IN (
    SELECT DISTINCT b.customer_id 
    FROM Booking b 
    JOIN Event e ON b.event_id = e.event_id
    WHERE e.venue_id = 1 -- Change 1 to the venue_id you want to filter
);

-- 10. Calculate the Total Number of Tickets Sold for Each Event Category Using a Subquery with GROUP BY
SELECT event_type, SUM(num_tickets) AS total_tickets_sold
FROM Event e
JOIN Booking b ON e.event_id = b.event_id
GROUP BY event_type;

-- 11. Find Users Who Have Booked Tickets for Events in Each Month Using a Subquery with DATE_FORMAT
SELECT customer_id, customer_name, 
       (SELECT COUNT(*) FROM Booking b WHERE b.customer_id = c.customer_id 
        AND DATE_FORMAT(b.booking_date, '%Y-%m') = '2024-06') AS total_bookings
FROM Customer c;

-- 12. Calculate the Average Ticket Price for Events in Each Venue Using a Subquery
SELECT venue_name, 
       (SELECT AVG(ticket_price) FROM Event WHERE Event.venue_id = Venue.venue_id) AS avg_ticket_price
FROM Venue;

