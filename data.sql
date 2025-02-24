USE ToDoList;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS;
SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION;
SET NAMES utf8mb4;
SET @OLD_TIME_ZONE=@@TIME_ZONE;
SET TIME_ZONE='+00:00';
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0;

DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
                            `ID` int(11) NOT NULL AUTO_INCREMENT,
                            `username` varchar(255) NOT NULL,
                            `password` varchar(255) NOT NULL,
                            `display_name` varchar(256) DEFAULT NULL,
                            PRIMARY KEY (`ID`),
                            UNIQUE KEY `tbl_user_pk` (`username`)
);

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;

INSERT INTO `tbl_user` VALUES
    (1, 'admin3', '$2a$10$PrlN/6A0qLWjOgVbpD7eROGtj9XgW7qGWnW8aRGbJh3TbfmhTUsPO', 'Adm3in2');

/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;

SET @OLD_CHARACTER_SET_CLIENT = @@character_set_client;
/* Removing the problematic line */
/* SET CHARACTER_SET_CLIENT = utf8mb4; */
/* Removing the problematic line */
/* SET CHARACTER_SET_CLIENT = @saved_cs_client; */
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET SQL_MODE = @OLD_SQL_MODE;
SET SQL_NOTES = @OLD_SQL_NOTES;
