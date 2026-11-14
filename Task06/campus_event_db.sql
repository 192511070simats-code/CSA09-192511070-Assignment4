CREATE DATABASE campus_event_db;

USE campus_event_db;

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(30) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    event_id INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_registration_event
        FOREIGN KEY (event_id)
        REFERENCES events(event_id),

    CONSTRAINT unique_student_event
        UNIQUE (student_id, event_id)
);

INSERT INTO events (event_name) VALUES
('Cultural Fest'),
('Music Competition'),
('Dance Competition'),
('Technical Symposium'),
('Sports Meet');

SELECT * FROM events;

SHOW TABLES;