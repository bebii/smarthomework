-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 27.254.63.25    Database: smartHomework
-- ------------------------------------------------------
-- Server version	5.5.49-0ubuntu0.14.04.1

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
-- Table structure for table `CorrectAns`
--

DROP TABLE IF EXISTS `CorrectAns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CorrectAns` (
  `CorrectID` int(20) NOT NULL AUTO_INCREMENT,
  `Answerfk` int(20) NOT NULL,
  `AnsDesc` text NOT NULL,
  PRIMARY KEY (`CorrectID`),
  KEY `Answerfk` (`Answerfk`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CorrectAns`
--

LOCK TABLES `CorrectAns` WRITE;
/*!40000 ALTER TABLE `CorrectAns` DISABLE KEYS */;
INSERT INTO `CorrectAns` VALUES (1,5,'เพราะคําว่า Thai เป็นการบอกสัญชาติขณะที่ Thailand เป็นการบอกชื่อประเทศ'),(2,8,'เพราะสถานการณ์ที่กําหนดให้ถามถึงหมายเลขโทรศัพท์'),(3,14,'เพราะคําว่า Japan หมายถึงประเทศ ขณะที่คำว่า Japanese หมายถึง ภาษาญี่ปุ่น ซึ่งเป็นคําตอบที่สอดคล้องกับคําถาม '),(4,18,'เพราะตามสถานการณ์เมื่อทอมถกถามว่าว่ายน้ําได้ไหม? เขาตอบว่าได้แต่ว่ายไม่ค่อยเก่ง'),(5,23,'เพราะโจทย์เป็น Yes/No question ที่ขึ้นต้นด้วย Do you ….?\nคําตอบต้องเป็น Yes, I do. '),(6,29,'เพราะเป็นคําตอบที่ถูกต้องตามสถานการณ์ที่กําหนด ซึ่งการตอบคําถาม how are you? โดยทั่วไปมักจะตอบว่า Fine, thanks. '),(7,31,'เพราะโจทย์ถามถึงกิจวัตรประจําวัน คําตอบต้องใช้กริยาในรูป Present simple Tense '),(8,39,'เพราะเป็นประโยคที่ใช้ในการบอกราคา '),(9,42,'เพราะโจทย์ถามว่าพ่อประกอบอาชีพอะไร คําตอบที่ตรงกับคําถามที่สุดควรเป็นประโยค He’s a doctor. '),(10,49,'เพราะตามสถานการณ์ครูขอร้องให้นักเรียนเปิดหน้าต่างให้ดังนั้นประโยคตอบรับที่ถูกต้องควรเป็น Yes, of course. '),(11,51,'เพราะเมื่อยื่นส่งสิ่งของให้ผู้อื่น ตามมารยาทจะพูดประโยคว่า here you are '),(12,59,'เพราะตามมารยาทเมื่อมีคนให้สิ่งของต้องกล่าวขอบคุณ'),(13,65,'เพราะในสถานการณ์เราได้กล่าวขอบคุณที่ส่งเกลือให้ตามมารยาทอีกฝ่ายจะตอบว่า You’re welcome. '),(14,66,'เพราะจากโจทย์เป็นการรบกวนหรือขัดจังหวะผู้อื่น โดยมารยาทจะกล่าวว่า Excuse me ก่อนเสมอ'),(15,73,'เพราะตามสถานการณ์ประโยคแรกทอมบอกว่าไม่สบาย ดังนั้นประโยคที่สองต้องเป็นการอธิบายเพิ่มเติมว่าเขาเป็นหวัด'),(16,79,'เพราะสถานการณ์สุนีย์ถามว่าแต่งงานหรือยัง? ทอมควรตอบว่า “ยังไม่มี” ซึ่งสอดคล้องกับประโยคที่สองที่บอกว่า “แต่เขามีแฟนแล้ว”'),(17,81,'เพราะสถานการณ์ Beam ถาม Billy ว่าสัญชาติอะไร ซึ่งคําตอบควรเป็น I am English. '),(18,87,'เพราะคําตอบเป็นการบอกที่อยู่ที่ถูกต้อง'),(19,92,'เพราะเป็นประโยคที่ใช้ตอบรับประโยคขอร้องที่เหมาะสมที่สุด'),(20,98,'เพราะเมื่อมคนบอกว่าจะไปท่องเที่ยว ประโยคที่ใช้อวยพรจะพูดว่า Have a good time. '),(21,105,'เพราะตามสถานการณ์โสภาต้องการอ่านหนังเตรียมสอบจึงไปดูหนังไม่ได้ซึ่งเป็นคําตอบที่มีเหตุผลที่สุด ในการปฏิเสธการชักชวนของเพื่อน'),(22,107,'เพราะ Excuse me เป็นการกล่าวขึ้นต้นประโยคที่สุภาพ'),(23,112,'เพราะเป็นสํานวนที่เข้ากับสถานการณ์ Nicky บอกว่า “เป็นความคิดที่ดี” เพราะเขาต้องการไปดูหนังกับเพื่อนในวันรุ่งขึ้น'),(24,119,'เพราะจากสถานการณ์ Emmy อายุมากกว่า 3 ปี Billy จะใช้คําว่า older than …. ซึ่งเป็นการเปรียบเทียบอายุของทั้งสองคนในขั้นกว่า'),(25,124,'เพราะเมื่อถูกเชิญให้นั่งอย่างสุภาพ ตามมารยาทที่ดีเราควรกล่าวขอบคุณ'),(26,126,'เพราะตามสถานการณ์เป็นการแนะนําเพื่อนให้รู้จักกัน เมื่อเจอกันจะพูดประโยค nice to meet you. เพื่อทักทาย'),(27,133,'เพราะ คนถามถามถึงวันเกิด When is your birthday? ต้องตอบวันที่และเดือน'),(28,138,'เพราะแม่ตอบรับด้วยประโยค Here you are. พ่อต้องขอร้องให้แม่ส่งของหรือ\nยื่นสิ่งของให้'),(29,145,'เพราะ a glass of water สอดคล้องกับข้อมูลที่เขาบอก thirsty'),(30,146,'เพราะ คนตอบตอบอาการเจ็บป่วย และสํานวน What’s the matter? เป็นสํานวนที่ถามเกี่ยวกับอาการเจ็บป่วย');
/*!40000 ALTER TABLE `CorrectAns` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-25 16:14:38
