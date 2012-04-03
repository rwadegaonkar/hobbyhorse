-- phpMyAdmin SQL Dump
-- version 3.2.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 02, 2012 at 11:11 PM
-- Server version: 5.1.44
-- PHP Version: 5.3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `hobbyhorse`
--

-- --------------------------------------------------------

--
-- Table structure for table `badge`
--
CREATE DATABASE hobbyhorse;
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` VALUES(1, 'Guitar lesson', 0x69742077617320737570657262202121, 0, 'admin', 'admin', '2012-04-01 17:20:09', '2012-04-01 17:20:12', 1, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` VALUES(1, 'none', 0x617765736f6d65206c6561726e696e6720657870657269656e6365, 0, 'admin', 'admin', '2012-04-01 17:49:26', '2012-04-01 17:49:29', 1, 5);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `forgotPassword`
--

INSERT INTO `forgotPassword` VALUES(1, 'noen', 0x6e6f656e, 0, 'admin', 'admin', '2012-04-01 18:05:33', '2012-04-01 18:05:37', 'what is ur fav passtime?', 'kidding', 1);

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
  `lessonTypeId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `lessonTypeId` (`lessonTypeId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `lesson`
--

INSERT INTO `lesson` VALUES(1, 'guitar', 0x6974206973206120626567696e6e657220636f75727365, 0, 'admin', 'admin', '2012-04-01 17:18:10', '2012-04-01 17:18:13', 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `lessonType`
--

INSERT INTO `lessonType` VALUES(1, 'music', 0x6d75736963, 0, 'admin', 'admin', '2012-04-01 17:18:51', '2012-04-01 17:18:53');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `loginType`
--


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `participants`
--


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
  `email` varchar(255) COLLATE utf8_bin NOT NULL,
  `skills` text COLLATE utf8_bin NOT NULL,
  `hobbies` text COLLATE utf8_bin NOT NULL,
  `location` varchar(255) COLLATE utf8_bin NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `roleId` (`roleId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=7 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES(1, 'Radhika Wadegaonkar', 0x626c6168, 0, 'admin', 'admin', '2012-04-01 15:29:02', '2012-04-01 15:29:05', 'radhika_wadegaonkar', 'radhika@gmail.com', 0x677569746172, 0x677569746172, 'mountain view', 0);
INSERT INTO `user` VALUES(2, 'Rad', 0x3b, 0, 'admin', 'admin', '2012-02-02 00:00:00', '2012-02-02 00:00:00', 'r', 'r', 0x72, 0x72, 'r', 0);
INSERT INTO `user` VALUES(3, 'Radhika Wadegaonka88r', 0x492077616e7420746f206c6561726e20677569746172, 0, 'admin', 'admin', '2012-04-01 00:00:00', '2012-04-01 00:00:00', 'radhika_w', 'radhika@gmail.com', 0x677569746172, 0x677569746172, 'mountain view', 0);
INSERT INTO `user` VALUES(4, 'Radhika Wadegaonka88r', 0x492077616e7420746f206c6561726e20677569746172, 0, 'admin', 'admin', '2012-04-01 00:00:00', '2012-04-01 00:00:00', 'radhika_w', 'radhika@gmail.com', 0x677569746172, 0x677569746172, 'mountain view', 0);
INSERT INTO `user` VALUES(5, 'Radhika Wadegaonka88r', 0x492077616e7420746f206c6561726e20677569746172, 0, 'admin', 'admin', '2012-04-01 15:45:17', '2012-04-01 15:45:17', 'radhika_w', 'radhika@gmail.com', 0x677569746172, 0x677569746172, 'mountain view', 0);
INSERT INTO `user` VALUES(6, 'Radhika Kulkarni', 0x492077616e7420746f206c6561726e20677569746172, 0, 'admin', 'admin', '2012-04-01 15:45:17', '2012-04-01 15:45:17', 'radhika_kulkarni', 'radhika1@gmail.com', 0x677569746172, 0x677569746172, 'mountain view', 0);

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
  ADD CONSTRAINT `comment_ibfk_4` FOREIGN KEY (`lessonId`) REFERENCES `lesson` (`id`),
  ADD CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

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
  ADD CONSTRAINT `friendsList_ibfk_2` FOREIGN KEY (`friendUserId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `friendsList_ibfk_1` FOREIGN KEY (`mainUserId`) REFERENCES `user` (`id`);

--
-- Constraints for table `lesson`
--
ALTER TABLE `lesson`
  ADD CONSTRAINT `lesson_ibfk_1` FOREIGN KEY (`lessonTypeId`) REFERENCES `lesson` (`id`);

--
-- Constraints for table `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`loginTypeId`) REFERENCES `loginType` (`id`);

--
-- Constraints for table `participants`
--
ALTER TABLE `participants`
  ADD CONSTRAINT `participants_ibfk_2` FOREIGN KEY (`lessonId`) REFERENCES `lesson` (`id`),
  ADD CONSTRAINT `participants_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints for table `redeemption`
--
ALTER TABLE `redeemption`
  ADD CONSTRAINT `redeemption_ibfk_1` FOREIGN KEY (`badgeId`) REFERENCES `badge` (`id`);

--
-- Constraints for table `userbadgemap`
--
ALTER TABLE `userbadgemap`
  ADD CONSTRAINT `userbadgemap_ibfk_1` FOREIGN KEY (`badgeId`) REFERENCES `badge` (`id`);

