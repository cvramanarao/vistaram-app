-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: vistaram
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Bank_Details`
--

DROP TABLE IF EXISTS `Bank_Details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Bank_Details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Bank_Name` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `Account_Name` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `Account_Number` int(20) NOT NULL,
  `IFSC_CODE` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `guest_details_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `guest_details_id_fk` (`guest_details_id`),
  CONSTRAINT `guest_details_id_fk` FOREIGN KEY (`guest_details_id`) REFERENCES `guest_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Bank_Details`
--

LOCK TABLES `Bank_Details` WRITE;
/*!40000 ALTER TABLE `Bank_Details` DISABLE KEYS */;
INSERT INTO `Bank_Details` VALUES (1,'ICICI','Willian Adam',222114482,'ICIC0001144',1),(2,'HDFC','Jessy Alwa',2147483647,'HDFC9908876',2),(3,'IDBI','Wilson James',2147483647,'IDBI5559966',3);
/*!40000 ALTER TABLE `Bank_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Booking_Details`
--

DROP TABLE IF EXISTS `Booking_Details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Booking_Details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Booking_Agent` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `Voucher_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Booking_Date` date NOT NULL,
  `Checking_Date` date NOT NULL,
  `checkout_Date` date NOT NULL,
  `No_Of_Nights_Stayed` int(4) NOT NULL,
  `Number_Of_Rooms` int(4) NOT NULL DEFAULT '1',
  `guest_id` int(10) NOT NULL,
  `hotel_details_id` int(11) NOT NULL,
  `properties_id` int(11) NOT NULL,
  `rate_plan_id` int(11) NOT NULL,
  `room_details_id` int(11) NOT NULL,
  `comments_or_requests` blob NOT NULL,
  PRIMARY KEY (`id`),
  KEY `hotel_details_id_fk` (`hotel_details_id`),
  KEY `Inclusions_properties_id_fk` (`properties_id`),
  KEY `Rate_Plan_id_fk` (`rate_plan_id`),
  KEY `room_details_id_fk` (`room_details_id`),
  CONSTRAINT `hotel_details_id_fk` FOREIGN KEY (`hotel_details_id`) REFERENCES `Hotel_Details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Inclusions_properties_id_fk` FOREIGN KEY (`properties_id`) REFERENCES `Inclusions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Rate_Plan_id_fk` FOREIGN KEY (`rate_plan_id`) REFERENCES `Rate_Plan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `room_details_id_fk` FOREIGN KEY (`room_details_id`) REFERENCES `Room_Details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Booking_Details`
--

LOCK TABLES `Booking_Details` WRITE;
/*!40000 ALTER TABLE `Booking_Details` DISABLE KEYS */;
/*!40000 ALTER TABLE `Booking_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hotel_Details`
--

DROP TABLE IF EXISTS `Hotel_Details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel_Details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `Place` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(55) COLLATE utf8_unicode_ci NOT NULL,
  `Zip_Code` int(20) NOT NULL,
  `contact_Info` int(10) NOT NULL,
  `Mail_ID` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `map` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel_Details`
--

LOCK TABLES `Hotel_Details` WRITE;
/*!40000 ALTER TABLE `Hotel_Details` DISABLE KEYS */;
/*!40000 ALTER TABLE `Hotel_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Inclusions`
--

DROP TABLE IF EXISTS `Inclusions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Inclusions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Property_Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Inclusions`
--

LOCK TABLES `Inclusions` WRITE;
/*!40000 ALTER TABLE `Inclusions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Inclusions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Rate_Plan`
--

DROP TABLE IF EXISTS `Rate_Plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rate_Plan` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `Rate_Plan_Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rate_Plan`
--

LOCK TABLES `Rate_Plan` WRITE;
/*!40000 ALTER TABLE `Rate_Plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `Rate_Plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room_Details`
--

DROP TABLE IF EXISTS `Room_Details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room_Details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Room_Name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Room_Type` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Room_Rate` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room_Details`
--

LOCK TABLES `Room_Details` WRITE;
/*!40000 ALTER TABLE `Room_Details` DISABLE KEYS */;
/*!40000 ALTER TABLE `Room_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest_booking_properties`
--

DROP TABLE IF EXISTS `guest_booking_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guest_booking_properties` (
  `property_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest_booking_properties`
--

LOCK TABLES `guest_booking_properties` WRITE;
/*!40000 ALTER TABLE `guest_booking_properties` DISABLE KEYS */;
/*!40000 ALTER TABLE `guest_booking_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest_details`
--

DROP TABLE IF EXISTS `guest_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guest_details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `First_Name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Last_Name` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `DOB` date NOT NULL,
  `Contact_Num` int(11) NOT NULL,
  `Email_Id` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Gender` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `Occupation` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Zip_Code` int(15) NOT NULL,
  `Address` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest_details`
--

LOCK TABLES `guest_details` WRITE;
/*!40000 ALTER TABLE `guest_details` DISABLE KEYS */;
INSERT INTO `guest_details` VALUES (1,'Willian','Adam','1965-12-12',993882774,'willian.adam@gmail.com','M','Dev',349822,'Sydney,Australia'),(2,'Alwa','Jessy','1962-03-10',2147483647,'alwa_jessy@yahoomail.com','F','HR Manager',2251,'NYC,USA'),(3,'Wilson','James','1970-05-11',12293445,'wilsonjames@hotmail.com','M','CEO',98217,'London,UK');
/*!40000 ALTER TABLE `guest_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-18 18:38:17
