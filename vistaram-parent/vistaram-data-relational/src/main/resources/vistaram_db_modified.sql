-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

drop database vistaram_db;
create database vistaram_db;
use vistaram_db;
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
CREATE  INDEX `users_username_UNIQUE` ON `hotel_details` (`users_username` ASC);

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

-- use vistaram_db;
insert into users values ('vistaramrooms@gmail.com', 'Welcome1', 1);
insert into authorities values('admin','vistaramrooms@gmail.com');
insert into users values ('hotelmadhurainn@gmail.com', 'Welcome1', 1);
insert into authorities values('user','hotelmadhurainn@gmail.com');
insert into users values ('info.vskp@microcontinental.in', 'Welcome1', 1);
insert into authorities values('user','info.vskp@microcontinental.in');
insert into users values ('applethehotel@gmail.com', 'Welcome1', 1);
insert into authorities values('user','applethehotel@gmail.com');
insert into users values ('om.rt@viharhospitality.com', 'Welcome1', 1);
insert into authorities values('user','om.rt@viharhospitality.com');
insert into users values ('arukuacrooms', 'Welcome1', 1);
insert into authorities values('user','arukuacrooms');
insert into users values ('vistaramairspacelucc', 'Welcome1', 1);
insert into authorities values('user','vistaramairspacelucc');
insert into users values ('vistaramairspacelohais', 'Welcome1', 1);
insert into authorities values('user','vistaramairspacelohais');
insert into users values ('vistaramairspacewaves', 'Welcome1', 1);
insert into authorities values('user','vistaramairspacewaves');
insert into users values ('vistaramazadsquare', 'Welcome1', 1);
insert into authorities values('user','vistaramazadsquare');
insert into users values ('vistaramarakuvalleyhotel', 'Welcome1', 1);
insert into authorities values('user','vistaramarakuvalleyhotel');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Madhura INN', '49-24-50/1, Sankaramatam Main Road, back side nistla nivas', 'Visakhapatnam', 'India', 'Vistaram Near Railway Station','hotelmadhurainn@gmail.com');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Micro Continental', 'Station Road', 'Visakhapatnam', 'India', 'Vistaram Ac Rooms Railway Station', 'info.vskp@microcontinental.in');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Apple Hotel', 'opp Rtc complex,near kfc, dwaraka nagar', 'Visakhapatnam', 'India', 'Vistaram Rtc Complex', 'applethehotel@gmail.com');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Vihar Boutique Hotel', 'Vinayagar Plaza, Airtel Building Sampath Vinayaka Temple Road', 'Visakhapatnam', 'India', 'Vistaram Near Rtc Complex', 'om.rt@viharhospitality.com');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Krishna Tara Comforts','Visakha-Araku Main Road','Araku Valley','India','Vistaram A/C Rooms','arukuacrooms');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Luck Residency','A-392/1,Road No.1,Near IGI Airport,NH-8 Highway,Mahipalpur Extn','Delhi','India','Vistaram Airspace Residency lucc','vistaramairspacelucc');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Hotel Lohias','A-53, Mahipalpur Extn. , National Highway -8 (Near AIRPORT)','Delhi','India','Vistaram Airspace Lohais','vistaramairspacelohais');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Waves Hotel','A-272, Mahipalpur Extn A- Block, NH-8, Near IGI Airport','Delhi','India','Vistaram Airspace Waves','vistaramairspacewaves');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Prime Plaza','Plot No. 7-9,Jharsa, Near Medanta Hospital,Sec. 3','Gurgoan','India','Vistaram Azad Square','vistaramazadsquare');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Vistaram Araku Valley Hotel','XXXXXXXXXXXXXXXXXXXX','Araku Valley','India','Vistaram Araku Valley Hotel','vistaramarakuvalleyhotel');

insert into hotel_details(hotel_name, address, city, country, hotel_identifier_name, users_username)

values('Vistaram Araku Valley','XXXXXXXXXXXXXXXXXXXX','Araku Valley','India','Vistaram Araku Valley','vistaramarakuvalleyhotel');

commit;	