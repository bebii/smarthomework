-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.7.16-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for smarthomework
CREATE DATABASE IF NOT EXISTS `smarthomework` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `smarthomework`;

-- Dumping structure for table smarthomework.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `AdminID` varchar(5) NOT NULL,
  `A_Name` varchar(100) NOT NULL,
  `A_Lname` varchar(100) NOT NULL,
  `A_Tel` int(10) NOT NULL,
  `A_Username` varchar(50) NOT NULL,
  `A_Password` varchar(20) NOT NULL,
  PRIMARY KEY (`AdminID`),
  UNIQUE KEY `A_Username` (`A_Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.admin_seq
CREATE TABLE IF NOT EXISTS `admin_seq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.answer
CREATE TABLE IF NOT EXISTS `answer` (
  `AnswerID` int(20) NOT NULL AUTO_INCREMENT,
  `Choice` varchar(10) NOT NULL,
  `Answer` text NOT NULL,
  `Questionfk` int(100) NOT NULL,
  PRIMARY KEY (`AnswerID`),
  KEY `Questionfk` (`Questionfk`),
  CONSTRAINT `fk_question_ans` FOREIGN KEY (`Questionfk`) REFERENCES `question` (`QuestionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.chapter
CREATE TABLE IF NOT EXISTS `chapter` (
  `ChapterID` int(20) NOT NULL AUTO_INCREMENT,
  `ChapterName` varchar(100) NOT NULL,
  `Subjectfk` varchar(5) NOT NULL,
  `AddBy` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  PRIMARY KEY (`ChapterID`),
  UNIQUE KEY `ChapterID` (`ChapterID`),
  KEY `subjectfk` (`Subjectfk`),
  CONSTRAINT `fk_subject_chap` FOREIGN KEY (`Subjectfk`) REFERENCES `subject` (`Subjectfk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.correctans
CREATE TABLE IF NOT EXISTS `correctans` (
  `CorrectID` int(20) NOT NULL AUTO_INCREMENT,
  `Answerfk` int(20) NOT NULL,
  `AnsDesc` text NOT NULL,
  PRIMARY KEY (`CorrectID`),
  KEY `Answerfk` (`Answerfk`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.exercise
CREATE TABLE IF NOT EXISTS `exercise` (
  `ExerID` int(20) NOT NULL AUTO_INCREMENT,
  `Chapterfk` int(20) NOT NULL,
  `DoDate` date NOT NULL,
  `ExpDate` date NOT NULL,
  `AmountDo` int(100) NOT NULL,
  `AddBy` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  `Score` varchar(5) NOT NULL,
  `Level` varchar(100) NOT NULL,
  PRIMARY KEY (`ExerID`),
  KEY `Chapterfk` (`Chapterfk`),
  CONSTRAINT `fk_chapter_exer` FOREIGN KEY (`Chapterfk`) REFERENCES `chapter` (`ChapterID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.homeworkset
CREATE TABLE IF NOT EXISTS `homeworkset` (
  `HsetID` int(20) NOT NULL AUTO_INCREMENT,
  `Score` int(100) NOT NULL,
  `SubmitDate` date DEFAULT NULL,
  `Studentfk` varchar(5) NOT NULL,
  PRIMARY KEY (`HsetID`),
  KEY `Studentfk` (`Studentfk`),
  CONSTRAINT `fk_student_hset` FOREIGN KEY (`Studentfk`) REFERENCES `student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.menu
CREATE TABLE IF NOT EXISTS `menu` (
  `MenuID` varchar(5) NOT NULL,
  `MenuName` varchar(100) NOT NULL,
  PRIMARY KEY (`MenuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.menu_seq
CREATE TABLE IF NOT EXISTS `menu_seq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.parent
CREATE TABLE IF NOT EXISTS `parent` (
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

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.parent_seq
CREATE TABLE IF NOT EXISTS `parent_seq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.permission
CREATE TABLE IF NOT EXISTS `permission` (
  `UserID` varchar(5) NOT NULL,
  `Menufk` varchar(5) NOT NULL,
  KEY `UserID` (`UserID`),
  KEY `Menufk` (`Menufk`),
  KEY `UserID_2` (`UserID`),
  CONSTRAINT `fk_admin_per` FOREIGN KEY (`UserID`) REFERENCES `admin` (`AdminID`),
  CONSTRAINT `fk_menu_per` FOREIGN KEY (`Menufk`) REFERENCES `menu` (`MenuID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_parent_per` FOREIGN KEY (`UserID`) REFERENCES `parent` (`ParentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_student_per` FOREIGN KEY (`UserID`) REFERENCES `student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_per` FOREIGN KEY (`UserID`) REFERENCES `teacher` (`TeacherID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.question
CREATE TABLE IF NOT EXISTS `question` (
  `QuestionID` int(100) NOT NULL AUTO_INCREMENT,
  `Proposition` text NOT NULL,
  `Exercisefk` int(20) NOT NULL,
  `Correct` varchar(1) NOT NULL,
  `AnsDesc` text NOT NULL,
  PRIMARY KEY (`QuestionID`),
  KEY `Exercisefk` (`Exercisefk`),
  CONSTRAINT `fk_exercise_quest` FOREIGN KEY (`Exercisefk`) REFERENCES `exercise` (`ExerID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.random
CREATE TABLE IF NOT EXISTS `random` (
  `Hsetfk` int(20) DEFAULT NULL,
  `Questionfk` int(100) NOT NULL,
  KEY `Hsetfk` (`Hsetfk`),
  KEY `Questionfk` (`Questionfk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.register
CREATE TABLE IF NOT EXISTS `register` (
  `Studentfk` varchar(5) NOT NULL,
  `Subjectfk` varchar(5) NOT NULL,
  `Semester` int(2) NOT NULL,
  `Year` date NOT NULL,
  `AddBy` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  KEY `Studentfk` (`Studentfk`),
  KEY `Subjectfk` (`Subjectfk`),
  CONSTRAINT `fk_stu_reg` FOREIGN KEY (`Studentfk`) REFERENCES `student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_subject_reg` FOREIGN KEY (`Subjectfk`) REFERENCES `subject` (`Subjectfk`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.student
CREATE TABLE IF NOT EXISTS `student` (
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

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.student_answer
CREATE TABLE IF NOT EXISTS `student_answer` (
  `HisID` int(20) NOT NULL AUTO_INCREMENT,
  `Studentfk` varchar(5) NOT NULL,
  `Questionfk` int(100) NOT NULL,
  `Answerfk` int(20) NOT NULL,
  PRIMARY KEY (`HisID`),
  KEY `Studentfk` (`Studentfk`),
  KEY `Questionfk` (`Questionfk`),
  KEY `Answerfk` (`Answerfk`),
  CONSTRAINT `fk_answer_stuans` FOREIGN KEY (`Answerfk`) REFERENCES `answer` (`AnswerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_question_stuans` FOREIGN KEY (`Questionfk`) REFERENCES `question` (`QuestionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_student_stuans` FOREIGN KEY (`Studentfk`) REFERENCES `student` (`StudentID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.student_seq
CREATE TABLE IF NOT EXISTS `student_seq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.subject
CREATE TABLE IF NOT EXISTS `subject` (
  `Subjectfk` varchar(5) NOT NULL,
  `Teacherfk` varchar(5) NOT NULL,
  `AddBy` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  UNIQUE KEY `Subjectfk` (`Subjectfk`),
  KEY `Teacherfk` (`Teacherfk`),
  KEY `Subjectfk_2` (`Subjectfk`),
  CONSTRAINT `fk_subject_sub` FOREIGN KEY (`Subjectfk`) REFERENCES `subjectname` (`SubjectID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_teacher_sub` FOREIGN KEY (`Teacherfk`) REFERENCES `teacher` (`TeacherID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.subjectname
CREATE TABLE IF NOT EXISTS `subjectname` (
  `SubjectID` varchar(5) NOT NULL,
  `SubjectName` varchar(50) NOT NULL,
  PRIMARY KEY (`SubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.subject_seq
CREATE TABLE IF NOT EXISTS `subject_seq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.teacher
CREATE TABLE IF NOT EXISTS `teacher` (
  `TeacherID` varchar(5) NOT NULL,
  `T_Name` varchar(100) NOT NULL,
  `T_Lname` varchar(100) NOT NULL,
  `T_Tel` varchar(20) NOT NULL,
  `T_Username` varchar(50) NOT NULL,
  `T_Password` varchar(20) NOT NULL,
  `Addby` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  PRIMARY KEY (`TeacherID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.teacher_seq
CREATE TABLE IF NOT EXISTS `teacher_seq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table smarthomework.test_exam
CREATE TABLE IF NOT EXISTS `test_exam` (
  `ExamID` int(20) NOT NULL AUTO_INCREMENT,
  `Chapterfk` int(20) NOT NULL,
  `Description` text NOT NULL,
  `ExamDate` date NOT NULL,
  `Addby` varchar(100) NOT NULL,
  `AddDate` datetime NOT NULL,
  PRIMARY KEY (`ExamID`),
  KEY `Chapterfk` (`Chapterfk`),
  CONSTRAINT `fk_chapter_exam` FOREIGN KEY (`Chapterfk`) REFERENCES `chapter` (`ChapterID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
