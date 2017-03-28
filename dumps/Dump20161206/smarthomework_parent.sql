-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: smarthomework
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent` (
  `ParentID` varchar(5) NOT NULL,
  `P_Name` varchar(100) NOT NULL,
  `P_Lname` varchar(100) NOT NULL,
  `P_Tel` varchar(20) NOT NULL,
  `P_Username` varchar(50) NOT NULL,
  `P_Password` varchar(20) NOT NULL,
  `Addby` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  PRIMARY KEY (`ParentID`),
  UNIQUE KEY `P_Username` (`P_Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES ('P0004','Pinla','Chalin','0215489632','Pinla','55555','Adminnaja','2016-11-21 02:00:00'),('P0005','Taksaorn','Sangsri','0874123652','Taksaorn','36521','Adminnaja','2016-11-21 02:00:00'),('P0006','Tikky','Manan','0458541236','Tikky','32587','Adminnaja','2016-11-21 02:00:00'),('P0007','Olydeyea','Peepan','0412569852','Olydeyea','14852','Adminnaja','2016-11-21 02:00:00'),('P0008','Seera','Yoo','0149852364','Seera','99999','Adminnaja','2016-11-21 02:00:00'),('P0009','Nutto','Kurota','0125478963','Nutto','65412','Adminnaja','2016-11-21 02:00:00'),('P0010','Machalin','Joojy','0128745693','Joojy','123456789','Adminnaja','2016-11-21 02:00:00'),('P0011','Chaleaf','Koti','0125478541','Chaleaf','121212','Adminnaja','2016-11-21 02:00:00'),('P0012','Haya','Zelliness','0147854125','Haya','020202','Adminnaja','2016-11-21 02:00:00'),('P0013','Anoma','Yeemin','0856861469','Bebii','12345','Adminnaja','2016-11-21 02:00:00'),('P0014','Parent','Yeemin','0856861469','P01','12345','Adminnaja','2016-11-21 02:00:00');
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-06 18:58:57
