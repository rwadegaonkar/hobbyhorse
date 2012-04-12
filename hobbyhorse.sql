-- phpMyAdmin SQL Dump
-- version 3.2.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 12, 2012 at 01:36 PM
-- Server version: 5.1.44
-- PHP Version: 5.3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hobbyhorse`
--

-- --------------------------------------------------------

--
-- Table structure for table `badge`
--

CREATE TABLE `badge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `badge`
--

INSERT INTO `badge` VALUES(1, 'Novice', 0x626c6168, 0, 'admin', 'admin', '2012-04-01 17:01:00', '2012-04-01 17:01:04');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `userId` int(11) NOT NULL,
  `lessonId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `userId` (`userId`),
  KEY `lessonId` (`lessonId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `comment`
--


-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `userId` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `feedback`
--


-- --------------------------------------------------------

--
-- Table structure for table `forgotPassword`
--

CREATE TABLE `forgotPassword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `question` varchar(255) COLLATE utf8_bin NOT NULL,
  `answer` varchar(255) COLLATE utf8_bin NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `forgotPassword`
--


-- --------------------------------------------------------

--
-- Table structure for table `friendsList`
--

CREATE TABLE `friendsList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `mainUserId` int(11) NOT NULL,
  `friendUserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `mainUserId` (`mainUserId`),
  KEY `friendUserId` (`friendUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `friendsList`
--


-- --------------------------------------------------------

--
-- Table structure for table `lesson`
--

CREATE TABLE `lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `eventDate` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `eventTime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `lessonTypeId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `sessionId` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `lessonTypeId` (`lessonTypeId`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=36 ;

--
-- Dumping data for table `lesson`
--

INSERT INTO `lesson` VALUES(23, 'Boxing', 0x57652077696c6c2068617665206f6e6520686f7572206f6620626f78696e672066756e20212121, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-11 22:27:55', '2012-04-11 22:27:55', 'April 12, 2012', '14:00', 1, 2, '1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDA1OjQwOjI3LjU3MDY1NCswMDowMH4wLjY5NzM3MTc3ODQ3N34');
INSERT INTO `lesson` VALUES(31, 'Dancing', 0x3120686f75722073657373696f6e, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-11 22:27:55', '2012-04-11 22:27:55', 'April 30, 2012', '14:00', 1, 2, '2_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDA2OjExOjM1LjAyMjU2MSswMDowMH4wLjY2NDY0NjkyMjA3NH4');
INSERT INTO `lesson` VALUES(33, 'Radhika', 0x3120686f75722073657373696f6e, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-11 22:27:55', '2012-04-11 22:27:55', 'April 12, 2012', '14:00', 3, 2, '1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDA2OjE0OjA2LjE0OTY2MSswMDowMH4wLjY1MjkyNjE1NDI3MX4');
INSERT INTO `lesson` VALUES(34, 'Boxing', 0x3220686f75722073657373696f6e, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-12 01:23:23', '2012-04-12 01:23:23', 'April 15, 2012', '17:00', 3, 2, '1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDEwOjA5OjQxLjE5ODI2OCswMDowMH4wLjE3MTM5OTY5MDk2Mn4');
INSERT INTO `lesson` VALUES(35, 'Mandolin', 0x57652074616b6520746869732075702c20696e2031306d696e7574657321, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-12 12:30:19', '2012-04-12 12:30:19', '04/10/2012', '17:00', 1, 2, '1_MX41ODc1MjMxfjEyNy4wLjAuMX4yMDEyLTA0LTEyIDE5OjU0OjA4Ljg1ODEzMyswMDowMH4wLjE5OTQxNzExMzAwMX4');

-- --------------------------------------------------------

--
-- Table structure for table `lessonType`
--

CREATE TABLE `lessonType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

--
-- Dumping data for table `lessonType`
--

INSERT INTO `lessonType` VALUES(1, 'Music', NULL, 0, 'admin', 'admin', '2012-04-08 23:35:04', '2012-04-08 23:35:07');
INSERT INTO `lessonType` VALUES(2, 'Art', NULL, 0, 'admin', 'admin', '2012-04-08 23:35:16', '2012-04-08 23:35:19');
INSERT INTO `lessonType` VALUES(3, 'Sports', NULL, 0, 'admin', 'admin', '2012-04-11 21:57:32', '2012-04-11 21:57:35');
INSERT INTO `lessonType` VALUES(4, 'Coding', NULL, 0, 'admin', 'admin', '2012-04-11 21:57:55', '2012-04-11 21:57:58');
INSERT INTO `lessonType` VALUES(5, 'Others', NULL, 0, 'admin', 'admin', '2012-04-11 21:58:31', '2012-04-11 21:58:33');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `loginTypeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `loginTypeId` (`loginTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `login`
--


-- --------------------------------------------------------

--
-- Table structure for table `loginType`
--

CREATE TABLE `loginType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `loginType`
--

INSERT INTO `loginType` VALUES(1, 'hobbyhorse', NULL, 0, 'admin', 'admin', '2012-04-10 23:12:33', '2012-04-10 23:12:35');
INSERT INTO `loginType` VALUES(2, 'facebook', NULL, 0, 'admin', 'admin', '2012-04-10 23:12:47', '2012-04-10 23:12:50');

-- --------------------------------------------------------

--
-- Table structure for table `participants`
--

CREATE TABLE `participants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `userId` int(11) NOT NULL,
  `lessonId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `userId` (`userId`),
  KEY `lessonId` (`lessonId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=39 ;

--
-- Dumping data for table `participants`
--

INSERT INTO `participants` VALUES(33, 'radhika.wadegaonkar', 0x6e756c6c, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-12 01:20:38', '2012-04-12 01:20:38', 2, 31);
INSERT INTO `participants` VALUES(34, 'radhika.wadegaonkar', 0x6e756c6c, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-12 01:20:38', '2012-04-12 01:20:38', 2, 33);
INSERT INTO `participants` VALUES(35, 'vaibhavkulkarni', 0x6e756c6c, 0, 'vaibhavkulkarni', 'vaibhavkulkarni', '2012-04-12 12:19:02', '2012-04-12 12:19:02', 23, 23);
INSERT INTO `participants` VALUES(36, 'vaibhavkulkarni', 0x6e756c6c, 0, 'vaibhavkulkarni', 'vaibhavkulkarni', '2012-04-12 12:19:02', '2012-04-12 12:19:02', 23, 33);
INSERT INTO `participants` VALUES(37, 'radhika.wadegaonkar', 0x6e756c6c, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-12 12:35:37', '2012-04-12 12:35:37', 2, 35);
INSERT INTO `participants` VALUES(38, 'vaibhavkulkarni', 0x6e756c6c, 0, 'vaibhavkulkarni', 'vaibhavkulkarni', '2012-04-12 12:35:37', '2012-04-12 12:35:37', 23, 35);

-- --------------------------------------------------------

--
-- Table structure for table `redeemption`
--

CREATE TABLE `redeemption` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `badgeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `badgeId` (`badgeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `redeemption`
--


-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_2` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `role`
--


-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT 'admin',
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `skills` text COLLATE utf8_bin,
  `hobbies` text COLLATE utf8_bin,
  `location` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `loginTypeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `id_2` (`id`),
  KEY `loginTypeId` (`loginTypeId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=24 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES(2, 'Radhika Wadegaonkar Kulkarni', 0x6e756c6c, 0, 'radhika.wadegaonkar', 'radhika.wadegaonkar', '2012-04-11 21:31:02', '2012-04-11 21:31:02', 'radhika.wadegaonkar', 'radhika', '', '', 0x534c5320486f74656c2061742042657665726c792048696c6c73, '', 2);
INSERT INTO `user` VALUES(23, 'Sulagna', 0x6e756c6c, 0, 'sulagna', 'sulagna', '2012-04-12 11:26:03', '2012-04-12 11:26:03', 'sulagna', 'sulagna', 'sbal2050@gmail.com', 0x477569746172, 0x477569746172, 'San Jose', 1);

-- --------------------------------------------------------

--
-- Table structure for table `userbadgemap`
--

CREATE TABLE `userbadgemap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin,
  `isDeleted` tinyint(1) NOT NULL,
  `createdBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastUpdatedBy` varchar(255) COLLATE utf8_bin NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdateDate` datetime NOT NULL,
  `userId` int(11) NOT NULL,
  `badgeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `badgeId` (`badgeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `userbadgemap`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `comment_ibfk_4` FOREIGN KEY (`lessonId`) REFERENCES `lesson` (`id`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `forgotPassword`
--
ALTER TABLE `forgotPassword`
  ADD CONSTRAINT `forgotPassword_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `friendsList`
--
ALTER TABLE `friendsList`
  ADD CONSTRAINT `friendsList_ibfk_1` FOREIGN KEY (`mainUserId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `friendsList_ibfk_2` FOREIGN KEY (`friendUserId`) REFERENCES `user` (`id`);

--
-- Constraints for table `lesson`
--
ALTER TABLE `lesson`
  ADD CONSTRAINT `lesson_ibfk_3` FOREIGN KEY (`lessonTypeId`) REFERENCES `lessonType` (`id`),
  ADD CONSTRAINT `lesson_ibfk_4` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`loginTypeId`) REFERENCES `loginType` (`id`);

--
-- Constraints for table `participants`
--
ALTER TABLE `participants`
  ADD CONSTRAINT `participants_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `participants_ibfk_2` FOREIGN KEY (`lessonId`) REFERENCES `lesson` (`id`);

--
-- Constraints for table `redeemption`
--
ALTER TABLE `redeemption`
  ADD CONSTRAINT `redeemption_ibfk_1` FOREIGN KEY (`badgeId`) REFERENCES `badge` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_6` FOREIGN KEY (`loginTypeId`) REFERENCES `loginType` (`id`);

--
-- Constraints for table `userbadgemap`
--
ALTER TABLE `userbadgemap`
  ADD CONSTRAINT `userbadgemap_ibfk_1` FOREIGN KEY (`badgeId`) REFERENCES `badge` (`id`);
