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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `AnswerID` int(20) NOT NULL AUTO_INCREMENT,
  `Choice` varchar(10) NOT NULL,
  `Answer` text NOT NULL,
  `Questionfk` int(100) NOT NULL,
  PRIMARY KEY (`AnswerID`),
  KEY `Questionfk` (`Questionfk`),
  CONSTRAINT `fk_question_ans` FOREIGN KEY (`Questionfk`) REFERENCES `question` (`QuestionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,'A','This is Thailand.',1),(2,'B','Thailand.',1),(3,'C','I live in Thailand.',1),(4,'D','I’m Thai.',1),(5,'E','Is Thailand.',1),(6,'A','room 203',2),(7,'B','23/17 Soi 6',2),(8,'C','0-2589-1902',2),(9,'D','กท-8315',2),(10,'E','10 years old.',2),(11,'A','China',3),(12,'B','Japanese',3),(13,'C','Japan',3),(14,'D','Chinese',3),(15,'E','Thai',3),(16,'A','not much',4),(17,'B','not good',4),(18,'C','but not very well',4),(19,'D','I can’t',4),(20,'E','I do',4),(21,'A','I do',5),(22,'B','I don’t',5),(23,'C','I am',5),(24,'D','I am not',5),(25,'E','I here',5),(26,'A','It\'s me',6),(27,'B','Hello',6),(28,'C','Good',6),(29,'D','Fine',6),(30,'E','Nice',6),(31,'A','I woke up',7),(32,'B','I am getting up',7),(33,'C','I don’t get up',7),(34,'D','I got up',7),(35,'E','I get up',7),(36,'A','costs five hundred baht',8),(37,'B','five hundred baht cost',8),(38,'C','costs hundred five bath ',8),(39,'D','five hundred baht',8),(40,'E','five hundred baht all together',8),(41,'A','He goes to the doctor.',9),(42,'B','He likes a doctor.',9),(43,'C','He’s a doctor.',9),(44,'D','He lives with a doctor.',9),(45,'E','She\'s a farmer.',9),(46,'A','Yes, of course.',10),(47,'B','No, I don’t.',10),(48,'C','Yes, go ahead.',10),(49,'D','Please do.',10),(50,'E','do yourself.',10),(51,'A','I do',11),(52,'B','go ahead',11),(53,'C','Thank you',11),(54,'D','here you are',11),(55,'E','not now',11),(56,'A','No, I don’t like it.',12),(57,'B','No, not now.',12),(58,'C','No, thank you.',12),(59,'D','Yes,She can.',12),(60,'E','Thank you.',12),(61,'A','No.',13),(62,'B','Not at all.',13),(63,'C','You’re welcome.',13),(64,'D','Yes.',13),(65,'E','Never mind.',13),(66,'A','Can I help you?',14),(67,'B','Excuse me.',14),(68,'C','Do you remember me?',14),(69,'D','Who are you?',14),(70,'E','Do you like me?',14),(71,'A','All right.',15),(72,'B','I am not fine.',15),(73,'C','I am fine.',15),(74,'D','I have a cold.',15),(75,'E','It\'s me.',15),(76,'A','Yes, I’m',16),(77,'B','No, I am not',16),(78,'C','Yes, I have',16),(79,'D','No, I haven’t',16),(80,'E','Yeah',16),(81,'A','am English',17),(82,'B','go to English',17),(83,'C','like English people',17),(84,'D','teach English',17),(85,'E','am England',17),(86,'A','0-2589-1902.',18),(87,'B','23/17 Sabaijai Village.',18),(88,'C','Tiwanon Street.',18),(89,'D','Thungsonghong Laksi, Bangkok.',18),(90,'E','under overpass.',18),(91,'A','No, thank you.',19),(92,'B','No way.',19),(93,'C','Yes, not now.',19),(94,'D','Yes, of course.',19),(95,'E','No, never.',19),(96,'A','Take it easy.',20),(97,'B','Forget it.',20),(98,'C','Have a good time.',20),(99,'D','You are lucky.',20),(100,'E','good trip.',20);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-06 18:59:00
