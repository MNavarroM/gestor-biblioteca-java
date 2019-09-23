-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: bibliotecajuan
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `autores`
--

DROP TABLE IF EXISTS `autores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autores` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `anombre` varchar(80) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autores`
--

LOCK TABLES `autores` WRITE;
/*!40000 ALTER TABLE `autores` DISABLE KEYS */;
INSERT INTO `autores` VALUES (290,'Bernard Cornwell'),(291,'J. D. Salinger');
/*!40000 ALTER TABLE `autores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editoriales`
--

DROP TABLE IF EXISTS `editoriales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `editoriales` (
  `eid` int(11) NOT NULL AUTO_INCREMENT,
  `enombre` varchar(80) NOT NULL,
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editoriales`
--

LOCK TABLES `editoriales` WRITE;
/*!40000 ALTER TABLE `editoriales` DISABLE KEYS */;
INSERT INTO `editoriales` VALUES (112,'Edhasa'),(113,'Fontanar');
/*!40000 ALTER TABLE `editoriales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generos` (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gnombre` varchar(50) NOT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES (150,'Novela Negra'),(151,'Novela Histórica'),(153,'Filosofía');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libros`
--

DROP TABLE IF EXISTS `libros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libros` (
  `nref` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) NOT NULL,
  `portada` varchar(200) DEFAULT NULL,
  `isbn` varchar(13) DEFAULT NULL,
  `editorial` int(11) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `anyocompra` date DEFAULT NULL,
  `puntuacion` int(11) DEFAULT NULL,
  `edicion` varchar(30) DEFAULT NULL,
  `idioma` varchar(30) DEFAULT NULL,
  `leido` varchar(4) DEFAULT NULL,
  `comentario` varchar(250) DEFAULT NULL,
  `genero` int(11) DEFAULT NULL,
  `subgenero` int(11) DEFAULT NULL,
  `fechapublicacion` int(11) DEFAULT NULL,
  `fechaintroduccion` date DEFAULT NULL,
  `autor` int(11) DEFAULT NULL,
  `localizacion` int(11) DEFAULT NULL,
  `lugarcompra` int(11) DEFAULT NULL,
  `encuadernacion` varchar(11) DEFAULT NULL,
  `coleccion` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`nref`),
  KEY `anyocompra` (`anyocompra`),
  KEY `anyocompra_2` (`anyocompra`),
  KEY `nref` (`nref`),
  KEY `nref_2` (`nref`),
  KEY `editorial` (`editorial`),
  KEY `genero` (`genero`),
  KEY `subgenero` (`subgenero`),
  KEY `autor` (`autor`),
  KEY `localizacion` (`localizacion`),
  KEY `lugarcompra` (`lugarcompra`),
  CONSTRAINT `libros_ibfk_10` FOREIGN KEY (`autor`) REFERENCES `autores` (`aid`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `libros_ibfk_11` FOREIGN KEY (`localizacion`) REFERENCES `localizaciones` (`lid`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `libros_ibfk_12` FOREIGN KEY (`lugarcompra`) REFERENCES `lugarcompra` (`lcid`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `libros_ibfk_7` FOREIGN KEY (`editorial`) REFERENCES `editoriales` (`eid`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `libros_ibfk_8` FOREIGN KEY (`genero`) REFERENCES `generos` (`gid`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `libros_ibfk_9` FOREIGN KEY (`subgenero`) REFERENCES `generos` (`gid`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1694 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libros`
--

LOCK TABLES `libros` WRITE;
/*!40000 ALTER TABLE `libros` DISABLE KEYS */;
INSERT INTO `libros` VALUES (1689,'Muerte de Reyes','C:\\Users\\Mario\\Desktop\\Curso Java\\BibliotecaJuan\\imagenes\\9788435062572.jpg','',112,NULL,NULL,9,'','Español','Si','',151,NULL,NULL,'2018-09-11',290,74,76,'Tapa dura','Si');
/*!40000 ALTER TABLE `libros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localizaciones`
--

DROP TABLE IF EXISTS `localizaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `localizaciones` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `lnombre` varchar(40) NOT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localizaciones`
--

LOCK TABLES `localizaciones` WRITE;
/*!40000 ALTER TABLE `localizaciones` DISABLE KEYS */;
INSERT INTO `localizaciones` VALUES (74,'K-1');
/*!40000 ALTER TABLE `localizaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lugarcompra`
--

DROP TABLE IF EXISTS `lugarcompra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lugarcompra` (
  `lcid` int(11) NOT NULL AUTO_INCREMENT,
  `lcnombre` varchar(50) NOT NULL,
  PRIMARY KEY (`lcid`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lugarcompra`
--

LOCK TABLES `lugarcompra` WRITE;
/*!40000 ALTER TABLE `lugarcompra` DISABLE KEYS */;
INSERT INTO `lugarcompra` VALUES (76,'El Lapicero');
/*!40000 ALTER TABLE `lugarcompra` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-13 22:35:41
