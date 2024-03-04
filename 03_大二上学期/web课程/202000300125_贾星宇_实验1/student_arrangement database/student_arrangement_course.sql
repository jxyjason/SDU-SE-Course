-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: student_arrangement
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `courseid` int NOT NULL AUTO_INCREMENT,
  `coursename` varchar(45) COLLATE utf8_bin NOT NULL,
  `courseteacher` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `credit` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `coursedescription` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `choosecoursebegtime` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `choosecourseendtime` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `coursebegtime` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `courseendtime` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`courseid`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (27,'种柳树','Willow','2','willow老师手把手教你种柳树~','2021-11-11','2022-11-13','2-1-1','15-1-1'),(28,'看杨柳','Willow','3','在春天一起去郊游','2022-03-05','2022-05-05','1-2-1','5-2-1'),(29,'吹柳絮','Willow','1','“未若柳絮因风起”','2021-11-20','2022-05-05','4-3-5','6-5-5'),(30,'关于柳树的前世与今生','Willow','4','“无心插柳柳成荫”','2020-09-09','2024-09-09','1-5-5','15-5-5'),(33,'计算机引论','Jason','2','双语授课，需要记忆','2020-09-09','2020-09-13','1-2-2','15-2-2'),(34,'C++程序设计','Jason','2','此课程需要自学','2021-11-11','2021-12-12','1-5-5','12-5-5'),(35,'线性代数','Jason','3','此课程需要购买吉米多维奇自学','2021-01-01','2021-12-12','1-4-3','15-4-3'),(36,'概率论与数理统计','Jason','4','此课程需要多做题','2021-01-01','2022-01-01','2-5-1','15-5-1'),(37,'看星星','Star','1','陪你看天空，陪你看星星','1999-12-12','2022-12-12','1-6-5','15-6-5'),(38,'看月亮','Star','3','看月牙月满月盈缺','0001-01-01','9999-12-30','1-7-5','15-7-5'),(39,'星星的形成原理','Star','2','从尘埃到星球','0001-01-01','9999-12-30','1-3-5','15-3-5'),(40,'月亮的盈缺','Star','2','月亮被谁吃掉了？','0001-01-01','9999-12-30','1-2-5','15-2-5');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-03 19:25:57
