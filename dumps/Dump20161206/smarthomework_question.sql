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
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `QuestionID` int(100) NOT NULL AUTO_INCREMENT,
  `Proposition` text NOT NULL,
  `Exercisefk` int(20) NOT NULL,
  `Correct` varchar(1) NOT NULL,
  `AnsDesc` text NOT NULL,
  PRIMARY KEY (`QuestionID`),
  KEY `Exercisefk` (`Exercisefk`),
  CONSTRAINT `fk_exercise_quest` FOREIGN KEY (`Exercisefk`) REFERENCES `exercise` (`ExerID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Situation: Christ is asking Som about her nationality.\r\nChris : What is your nationality?\r\nSom : .................................. .',1,'D','เพราะคำว่า Thai เป็นการบอกสัญชาติ ขณะที่ Thailand เป็นการบอกชื่อประเทศ'),(2,'Situation: At the office\r\nTom : What’s your telephone number?\r\nJeed : It is ................................ .',1,'C','เพราะสถานการณ์ที่กำหนดให้นั้นถามถึงหมายเลขโทรศัพท์'),(3,'Situation: At the airport\r\nTom : Are you from Japan?\r\nYoko : Yes, and I speak ................................. .',1,'B','เพราะคำว่า Japan หมายถึงประเทศ ขณะที่คำว่า Japanese หมายถึง ภาษาญี่ปุ่น ซึ่งเป็นคำตอบที่สอดคล้องกับคำถาม'),(4,'Situation : Suda is asking Tom to go swimming.\r\nSuda : Can you swim?\r\nTom : Yes, .................................. .',1,'C','เพราะตามสถานการณ์เมื่อทอมถูกถามว่าว่ายน้ำได้ไหม? เขาตอบว่าได้แต่ว่ายไม่ค่อยเก่ง'),(5,'Situation : Jib is asking Suda about her hometown.\r\nJib : Do you live in Bangkok?\r\nSuda : Yes, ................................. .',1,'A','เพราะโจทย์เป็น Yes/No question ที่ขึ้นต้นด้วย Do you ….? คำตอบต้องเป็น Yes, I do.'),(6,'Situation : Ann meets Billy at school on Monday morning.\r\nAnn : How are you this morning?\r\nBilly : ................................. , thanks.',1,'D','เพราะเป็นคำตอบที่ถูกต้องตามสถานการณ์ที่กำหนด ซึ่งการตอบคำถาม how are you? โดยทั่วไปมักจะตอบว่า Fine, thanks.'),(7,'Situation: At the school\r\nJessica : What time do you get up?\r\nTodd : .................................. at six o’clock in the morning.',1,'E','เพราะโจทย์ถามถึงกิจวัตรประจำวัน คำตอบต้องใช้กริยาในรูป Present simple Tense'),(8,'Situation: At school canteen\r\nTom : Nice watch. How much does it cost?\r\nKitty : It is ................................. .',1,'D','เพราะเป็นประโยคที่ใช้ในการบอกราคา'),(9,'Situation: In English class\r\nTeacher : What does your father do?\r\nSuporn : .................................. .',1,'C','เพราะโจทย์ถามว่าพ่อประกอบอาชีพอะไร คำตอบที่ตรงกับคำถามที่สุดควรเป็นประโยค He’s a doctor.'),(10,'Situation: The teacher is asking Suporn to open the window.\r\nTeacher : Please open the window?\r\nSuporn : .................................. .',1,'A','เพราะตามสถานการณ์ครูขอร้องให้นักเรียนเปิดหน้าต่างให้ ดังนั้นประโยคตอบรับที่ถูกต้องควรเป็น Yes, of course.'),(11,'Situation: In Thai class.\r\nTeacher : Give me your exercise book, please.\r\nSupha : Yes, .................................. .',1,'D','เพราะเมื่อยื่นส่งสิ่งของให้ผู้อื่น ตามมารยาทจะพูดประโยคว่า here you are'),(12,'Situation: At the school canteen.\r\nSopa : Here you are, a glass of water.\r\nTeacher : .................................. .',1,'E','เพราะตามมารยาทเมื่อมีคนให้สิ่งของ ต้องกล่าวขอบคุณ'),(13,'Situation: At the restaurant\r\nEddy : Pass me the salt, please.\r\nSunee : Here you are.\r\nTeacher : Thank you.\r\nSunee : .................................. .',1,'C','เพราะในสถานการณ์เราได้กล่าวขอบคุณที่ส่งเกลือให้ ตามมารยาทอีกฝ่ายจะตอบว่า You’re welcome.'),(14,'Situation: At the airport\r\nClaire : ................................. . Are you Fred?\r\nFred : Yes, I am.',1,'B','เพราะจากโจทย์เป็นการรบกวนหรือขัดจังหวะผู้อื่น โดยมารยาทจะกล่าวว่า Excuse me ก่อนเสมอ'),(15,'Situation: At the school\r\nSuda : Are you all right, Tom?\r\nTom : No, I’m not. .................................. .',1,'D','เพราะตามสถานการณ์ ประโยคแรกทอมบอกว่าไม่สบาย ดังนั้นประโยคที่สองต้องเป็นการอธิบายเพิ่มเติมว่าเขาเป็นหวัด'),(16,'Situation: Sunee meets her friend Tom at the café.\r\nSunee : Are you married?\r\nTom : ............................. but I have a girlfriend.',1,'B','เพราะสถานการณ์ สุนีย์ถามว่าแต่งงานหรือยัง? ทอมควรตอบว่า “ยังไม่มี” ซึ่งสอดคล้องกับประโยคที่สองที่บอกว่า “แต่เขามีแฟนแล้ว”'),(17,'Situation: Beam is asking Billy\r\nBeam : What is your nationality?\r\nBilly : I ................................. .',1,'A','เพราะสถานการณ์ Beam ถาม Billy ว่าสัญชาติอะไร ซึ่งคำตอบควรเป็น I am English.'),(18,'Situation: At the police station\r\nPolice : What’s your address?\r\nTim : ................................ .',1,'B','เพราะคำตอบเป็นการบอกที่อยู่ที่ถูกต้อง'),(19,'Situation: In the classroom\r\nTeacher : It is raining. Can you close the windows, please?\r\nSuni : ................................ .',1,'D','เพราะเป็นประโยคที่ใช้ตอบรับประโยคขอร้องที่เหมาะสมที่สุด'),(20,'Situation: Taking a trip\r\nAndy : I’m going to the Korea tomorrow.\r\nRicky : ................................. .',1,'C','เพราะเมื่อมีคนบอกว่าจะไปท่องเที่ยว ประโยคที่ใช้อวยพรจะพูดว่า\r\nHave a good time.');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-06 18:58:59
