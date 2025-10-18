-- Create new database
CREATE DATABASE gdr;

-- Use created database
USE gdr;

-- Create booking table
CREATE TABLE `booking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `room_id` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `organizer` varchar(100) NOT NULL,
  `status` enum('ACTIVE','CANCELLED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`booking_id`),
  KEY `fk_room` (`room_id`),
  CONSTRAINT `fk_room` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE
)

-- Create room table
CREATE TABLE `room` (
  `room_id` varchar(50) NOT NULL,
  PRIMARY KEY (`room_id`)
)
