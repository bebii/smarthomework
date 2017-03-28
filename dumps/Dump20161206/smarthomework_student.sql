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
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `StudentID` varchar(5) NOT NULL,
  `S_Name` varchar(100) NOT NULL,
  `S_Lname` varchar(100) NOT NULL,
  `S_Tel` varchar(20) NOT NULL,
  `S_Username` varchar(50) NOT NULL,
  `S_Password` varchar(20) NOT NULL,
  `Parentfk` varchar(5) NOT NULL,
  `Addby` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  PRIMARY KEY (`StudentID`),
  UNIQUE KEY `S_Username` (`S_Username`),
  KEY `Parentfk` (`Parentfk`),
  CONSTRAINT `fk_parent_stu` FOREIGN KEY (`Parentfk`) REFERENCES `parent` (`ParentID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('S0001','Pitchy','jayjay','0147852369','pitchy','12345','P0004','Adminnaja','2016-11-21 02:05:06'),('S0002','Firsty','Shawatida','0236547891','Firsty','12345','P0005','Adminnaja','2016-11-21 02:06:08'),('S0003','Lookkai','Kookkook','0874569874','lookkai','12345','P0006','Adminnaja','2016-11-21 02:06:08'),('S0004','Yayee','Wimolkan','0987778987','yayee','12345','P0007','Adminnaja','2016-11-21 02:06:08'),('S0005','Chalee','Moon','0125478963','chalee','12345','P0008','Adminnaja','2016-11-21 02:06:08'),('S0006','Sasithorn','Khajee','0258746987','Keaky','12345','P0009','Adminnaja','2016-11-21 02:06:08'),('S0007','Nana','Hosney','0875478965','nana','12345','P0010','Adminnaja','2016-11-21 02:06:08'),('S0008','Natwara','Asmah','0147854789','natwara','12345','P0011','Adminnaja','2016-11-21 02:06:08'),('S0009','Panita','Rosey','0145825413','Nhing','12345','P0012','Adminnaja','2016-11-21 02:06:08'),('S0010','kasih','Sayang','0956304876','kekasihku','12345','P0013','Adminnaja','2016-11-21 02:06:08'),('S0012','Yutta','Sayang','0956304876','S01','12345','P0013','Adminnaja','2016-11-21 02:06:08');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-06 18:58:58
