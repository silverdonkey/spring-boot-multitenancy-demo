--
-- Databases: 'mydb1' 'mydb2'
--
CREATE DATABASE IF NOT EXISTS `mydb1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS `mydb2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

--
-- User: 'dbuser'
--
CREATE USER IF NOT EXISTS 'dbuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON mydb1.* TO 'dbuser'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mydb2.* TO 'dbuser'@'localhost' WITH GRANT OPTION;

CREATE USER IF NOT EXISTS 'dbuser'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON mydb1.* TO 'dbuser'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mydb2.* TO 'dbuser'@'%' WITH GRANT OPTION;


USE `mydb1`;

DROP TABLE IF EXISTS `genericentity`;

CREATE TABLE IF NOT EXISTS `genericentity` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=INNODB;

--
-- Dumping data for tables `genericentity`
--

LOCK TABLES `genericentity` WRITE;
/*!40000 ALTER TABLE `genericentity` DISABLE KEYS */;
INSERT INTO `genericentity`
VALUES (1, 'MYDB1_Entity_1'),
       (2, 'MYDB1_Entity_2'),
       (3, 'MYDB1_Entity_3');

/*!40000 ALTER TABLE `genericentity` ENABLE KEYS */;
UNLOCK TABLES;

USE `mydb2`;
--
-- Database: 'mydb2'
--

DROP TABLE IF EXISTS `genericentity`;

CREATE TABLE IF NOT EXISTS `genericentity` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=INNODB;

--
-- Dumping data for tables `genericentity`
--

LOCK TABLES `genericentity` WRITE;
/*!40000 ALTER TABLE `genericentity` DISABLE KEYS */;
INSERT INTO `genericentity`
VALUES (1, 'MYDB2_Entity_1'),
       (2, 'MYDB2_Entity_2');

/*!40000 ALTER TABLE `genericentity` ENABLE KEYS */;
UNLOCK TABLES;
