-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `guest_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `guest_details` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `guest_details` (
  `guest_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(120) NULL,
  `last_name` VARCHAR(120) NULL,
  `dob` DATE NULL,
  `contact_number` VARCHAR(45) NULL,
  `email_id` VARCHAR(120) NULL,
  PRIMARY KEY (`guest_id`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `hotel_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel_details` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `hotel_details` (
  `hotel_id` INT NOT NULL AUTO_INCREMENT,
  `hotel_name` VARCHAR(120) NOT NULL,
  `city` VARCHAR(120) NULL,
  `address` VARCHAR(120) NULL,
  `pincode` VARCHAR(6) NULL,
  `country` VARCHAR(120) NULL,
  `hotel_identifier_name` VARCHAR(150) NOT NULL,
  `users_username` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`hotel_id`),
  CONSTRAINT `fk_hotel_details_users1`
    FOREIGN KEY (`users_username`)
    REFERENCES `users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `hotel_identifier_name_UNIQUE` ON `hotel_details` (`hotel_identifier_name` ASC);

SHOW WARNINGS;
CREATE UNIQUE INDEX `users_username_UNIQUE` ON `hotel_details` (`users_username` ASC);

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `booking_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `booking_details` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `booking_details` (
  `booking_id` INT NOT NULL AUTO_INCREMENT,
  `booking_agent` VARCHAR(120) NOT NULL,
  `voucher_id` VARCHAR(120) NOT NULL,
  `booking_date` DATETIME NOT NULL,
  `checkin_date` DATE NOT NULL,
  `checkout_date` DATE NOT NULL,
  `no_of_rooms` INT NOT NULL,
  `no_of_nights` INT NOT NULL,
  `payment_type` ENUM('AT_HOTEL', 'ONLINE') NOT NULL,
  `guest_details_guest_id` INT NOT NULL,
  `hotel_details_hotel_id` INT NOT NULL,
  `total_tax` DOUBLE NOT NULL,
  `total_amout` DOUBLE NOT NULL,
  PRIMARY KEY (`booking_id`),
  CONSTRAINT `fk_booking_details_guest_details`
    FOREIGN KEY (`guest_details_guest_id`)
    REFERENCES `guest_details` (`guest_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_details_hotel_details1`
    FOREIGN KEY (`hotel_details_hotel_id`)
    REFERENCES `hotel_details` (`hotel_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `voucher_id_UNIQUE` ON `booking_details` (`voucher_id` ASC);

SHOW WARNINGS;
CREATE INDEX `fk_booking_details_guest_details_idx` ON `booking_details` (`guest_details_guest_id` ASC);

SHOW WARNINGS;
CREATE INDEX `fk_booking_details_hotel_details1_idx` ON `booking_details` (`hotel_details_hotel_id` ASC);

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `room_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `room_details` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `room_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `room_name` VARCHAR(6) NOT NULL,
  `room_type` VARCHAR(45) NOT NULL,
  `room_rate` DOUBLE NOT NULL,
  `no_of_adults` INT NOT NULL,
  `no_of_children` INT NOT NULL DEFAULT 0,
  `booking_details_booking_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_room_details_booking_details1`
    FOREIGN KEY (`booking_details_booking_id`)
    REFERENCES `booking_details` (`booking_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_room_details_booking_details1_idx` ON `room_details` (`booking_details_booking_id` ASC);

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `tariff_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tariff_details` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `tariff_details` (
  `tariff_details_id` INT NOT NULL AUTO_INCREMENT,
  `checkin_date` DATE NOT NULL,
  `checkout_date` DATE NOT NULL,
  `no_of_nights` INT NOT NULL DEFAULT 1,
  `rate_per_room` DOUBLE NOT NULL,
  `total_rate` DOUBLE NOT NULL,
  `booking_details_booking_id` INT NOT NULL,
  PRIMARY KEY (`tariff_details_id`),
  CONSTRAINT `fk_tariff_details_booking_details1`
    FOREIGN KEY (`booking_details_booking_id`)
    REFERENCES `booking_details` (`booking_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_tariff_details_booking_details1_idx` ON `tariff_details` (`booking_details_booking_id` ASC);

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `authorities` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `authorities` (
  `authority` VARCHAR(50) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`, `authority`),
  CONSTRAINT `fk_authorities_users1`
    FOREIGN KEY (`username`)
    REFERENCES `users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `authorities_idx` ON `authorities` (`authority` ASC, `username` ASC);

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

use vistaram_db;
insert into users values ('user1', 'user1', 1);
insert into authorities values('user','user1');
insert into hotel_details (hotel_name, city, hotel_identifier_name, users_username) values('Vistaram Near Rtc Complex','Visakhapatnam','Vistaram Near Rtc Complex, Visakhapatnam', 'user1');
insert into users values ('user2', 'user2', 1);
insert into authorities values('user','user2');
insert into hotel_details (hotel_name, city, hotel_identifier_name, users_username) values('Vistaram Rtc Complex','Visakhapatnam','Vistaram Rtc Complex, Visakhapatnam', 'user2');
insert into users values ('user3', 'user3', 1);
insert into authorities values('user','user3');
insert into hotel_details (hotel_name, city, hotel_identifier_name, users_username) values('Vistaram Near Railway Station','Visakhapatnam','Vistaram Near Railway Station, Visakhapatnam', 'user3');
insert into users values ('user4', 'user4', 1);
insert into authorities values('user','user4');
insert into hotel_details (hotel_name, city, hotel_identifier_name, users_username) values('Vistaram Ac Rooms Railway Station','Visakhapatnam','Vistaram Ac Rooms Railway Station, Visakhapatnam', 'user4');
commit;