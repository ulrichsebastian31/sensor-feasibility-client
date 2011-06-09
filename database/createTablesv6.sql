-- phpMyAdmin SQL Dump
-- version 3.2.1
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Mar 30 Novembre 2010 à 15:47
-- Version du serveur: 5.1.37
-- Version de PHP: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de données: `hmafo`
--

-- --------------------------------------------------------

--
-- Structure de la table `tasks`
--

CREATE TABLE IF NOT EXISTS `tasks` (
  `TASK_ID` varchar(100) NOT NULL,
  `TASK_USER` varchar(30) NOT NULL,
  `TASK_NAME` varchar(50) NOT NULL,
  `TASK_OPERATION` varchar(20) NOT NULL,
  `TASK_STATUS` varchar(20) DEFAULT NULL,
  `TASK_SENSOR` varchar(70) NOT NULL,
  `TASK_REQUEST` text,
  `TASK_REQUEST_STATUS` varchar(20) NOT NULL,
  `TASK_RESPONSE` mediumtext,
  `TASK_RESPONSE_TYPE` varchar(14) NOT NULL,
  `TASK_CREATION` datetime NOT NULL,
  `TASK_MODIFICATION` datetime NOT NULL,
  `TASK_UPDATED` int(2) NOT NULL DEFAULT '0',
  UNIQUE KEY `TASK_ID_IDX` (`TASK_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
