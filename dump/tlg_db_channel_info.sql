-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: tlg_db
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `channel_info`
--

DROP TABLE IF EXISTS `channel_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channel_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_category` int(11) DEFAULT NULL,
  `channel_description` varchar(256) DEFAULT NULL,
  `channel_name` varchar(56) DEFAULT NULL,
  `channel_subscribers` int(11) DEFAULT NULL,
  `channel_url` varchar(56) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channel_info`
--

LOCK TABLES `channel_info` WRITE;
/*!40000 ALTER TABLE `channel_info` DISABLE KEYS */;
INSERT INTO `channel_info` VALUES (1,101,'top channel in ux world','uxidesign',1000,'https://t.me/uxidesign'),(2,101,'2 channel in ux world','freshsmm',1000,'https://t.me/uxidesign'),(3,102,'it channel','uxidesign',1000,'urlchannel'),(4,102,'top  IT channels in ux world','uxidesign',121000,'@uxidesign'),(5,101,'description of my channel','turbofresh',1000,'@uxidesign'),(6,102,'description of my channel','blockchainJournal',1000,'@uxidesign'),(7,102,'description of my channel','top4chan',10000,'@uxidesign'),(8,102,'description of my channel','again',5000,'@uxidesign');
/*!40000 ALTER TABLE `channel_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-09 18:30:10
