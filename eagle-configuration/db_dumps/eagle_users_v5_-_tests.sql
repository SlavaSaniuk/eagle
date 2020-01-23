-- MySQL dump 10.13  Distrib 5.5.62, for debian-linux-gnu (x86_64)
--
-- Host: 10.8.8.110    Database: eagle_users
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` int(9) NOT NULL,
  `account_email` varchar(255) NOT NULL,
  `account_password_hash` varchar(128) NOT NULL,
  `account_password_salt` varchar(128) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_email` (`account_email`),
  UNIQUE KEY `UK_o72icrlyfe9jcc61hbgsnmkgr` (`account_email`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'saniuk.vyacheslav.97@gmail.com','F7A48D3C42E662EEB5F377F561FC448DB1E904AECD6432123633F2BC5421BE1636F73FBAA6AAAFBCF561B27BEC4779932388FE05F1C049B3DB201BF5EE9FAA67','8026BAAEB28327585DBD92BD4A917295729C2651363721FA83226D8792A73FF6ED6675321F37245A17F6EEFC24DFA096F43FEAF1A2F3D200645DCB64FE893DE2'),(2,'A.Tkachov@bsac.by','A27723F40A6F9DC3BA3D97AE1C262462FDD12A7DC2FEA14D825A583BCC1EBE04D73334200958284D968A88BA0F97DA269D10A4E43AB29BBD1A796ACD5501230D','456F32CF576D5A89534F63F1B0A108B3943D084446F23A088A86CDDFDF3BA3AF8D78156F40CA4A0B179B1397945E919A7E76FFACCC13014989AE5411F3AA0CD7'),(3,'anzhelika.korneeva@mail.ru','85D381DBB1B8B7E90FC527FBDA549E8A6CD168EA812936F60DAD805AE7E05D930B7B26488D9B413036D5560695187A2BAA74958949B21E807A5D2C1C20A11950','2FF403C8512A53B44A35D68BDFD2BEA7343A2985E338EED72422395C24DEED16BBE2EB07C4D8EA8F4FC7C78F80A733C0EC84AEF4BC04C4486580CB2006A4DA9C'),(4,'arseniy_savoschik@yandex.ru','21BB0FA75C38C1897575EEA0BC36A7F8CD50C27DE1A2371D05A6C306E074459ADAB1E437ADB718D719ED3FA036FFE1644DD99F917A27C8E5333C7D0001D229E3','097F32CBFEA3954F3DE9BD72EF71DEFFDF8A7F4FE23CF803AE2D7BC31496DCD0FA2B25AC5A1268B65087349905B0066E9D91B324695B2A8E4E3F774B29594EDE');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_status`
--

DROP TABLE IF EXISTS `account_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_status` (
  `status_id` int(9) NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`status_id`),
  CONSTRAINT `account_status_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_status`
--

LOCK TABLES `account_status` WRITE;
/*!40000 ALTER TABLE `account_status` DISABLE KEYS */;
INSERT INTO `account_status` VALUES (1,'CONFIRMED'),(2,'CONFIRMED'),(3,'CONFIRMED'),(4,'CREATED');
/*!40000 ALTER TABLE `account_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image_file`
--

DROP TABLE IF EXISTS `image_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image_file` (
  `image_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `images_context` int(9) NOT NULL,
  `image_path` varchar(255) NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `image_file` (`images_context`),
  CONSTRAINT `image_file_ibfk_1` FOREIGN KEY (`images_context`) REFERENCES `user_images_context` (`context_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image_file`
--

LOCK TABLES `image_file` WRITE;
/*!40000 ALTER TABLE `image_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `image_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(9) NOT NULL AUTO_INCREMENT,
  `user_id_alias` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_alias` (`user_id_alias`),
  UNIQUE KEY `UK_43cdpnxsq4fjwf81ejpr3ktjx` (`user_id_alias`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL),(2,NULL),(3,NULL),(4,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_detail`
--

DROP TABLE IF EXISTS `user_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_detail` (
  `detail_id` int(9) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  PRIMARY KEY (`detail_id`),
  CONSTRAINT `user_detail_ibfk_1` FOREIGN KEY (`detail_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_detail`
--

LOCK TABLES `user_detail` WRITE;
/*!40000 ALTER TABLE `user_detail` DISABLE KEYS */;
INSERT INTO `user_detail` VALUES (1,'Vyacheslav','Saniuk'),(2,'Artyom','Tkachev'),(3,'Anzzhelika','Korneeva');
/*!40000 ALTER TABLE `user_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_images_context`
--

DROP TABLE IF EXISTS `user_images_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_images_context` (
  `context_id` int(9) NOT NULL,
  PRIMARY KEY (`context_id`),
  CONSTRAINT `user_images_context_ibfk_1` FOREIGN KEY (`context_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_images_context`
--

LOCK TABLES `user_images_context` WRITE;
/*!40000 ALTER TABLE `user_images_context` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_images_context` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-24  0:13:04
