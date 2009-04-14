-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema bakery
--

CREATE DATABASE IF NOT EXISTS bakery;
USE bakery;

--
-- Definition of table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `account_id` int(11) NOT NULL,
  `account_name` char(100) NOT NULL default '<unknown>',
  `account_active` int(11) NOT NULL default '1',
  `account_desc` char(250) default NULL,
  `account_contragent` int(11) NOT NULL,
  PRIMARY KEY  (`account_id`),
  KEY `account_contragent` (`account_contragent`),
  CONSTRAINT `FK_accounts_1` FOREIGN KEY (`account_contragent`) REFERENCES `contragents` (`contragent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `accounts`
--

/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` (`account_id`,`account_name`,`account_active`,`account_desc`,`account_contragent`) VALUES 
 (1,'&#1054;&#1089;&#1085;&#1086;&#1074;&#1085;&#1086;&#1081;',0,'',1),
 (2,'&#1042;&#1072;&#1083;&#1102;&#1090;&#1085;&#1099;&#1081;',1,'',1),
 (3,'&#1054;&#1089;&#1085;&#1086;&#1074;&#1085;&#1086;&#1081;',1,'',4),
 (4,'&#1054;&#1089;&#1085;&#1086;&#1074;&#1085;&#1086;&#1081;',1,'',2),
 (5,'&#1044;&#1083;&#1103; &#1074;&#1079;&#1103;&#1090;&#1086;&#1082;',1,'&#1044;&#1083;&#1103; &#1074;&#1079;&#1103;&#1090;&#1086;&#1082;',5),
 (6,'&#1042;&#1072;&#1083;&#1102;&#1090;&#1085;&#1099;&#1081;',1,'&#1044;&#1083;&#1103; &#1074;&#1079;&#1103;&#1090;&#1086;&#1082; &#1074; &#1074;&#1072;&#1083;&#1102;&#1090;&#1077;',5),
 (7,'&#1053;&#1077; &#1076;&#1083;&#1103; &#1074;&#1079;&#1103;&#1090;&#1086;&#1082;',0,'',5);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;


--
-- Definition of table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses` (
  `address_id` int(11) NOT NULL auto_increment,
  `address_address` char(250) default NULL,
  `address_active` int(11) NOT NULL default '1',
  PRIMARY KEY  (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `addresses`
--

/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` (`address_id`,`address_address`,`address_active`) VALUES 
 (1,'&#1055;&#1086;&#1079;&#1076;&#1085;&#1103;&#1082;&#1086;&#1074;&#1072; &#1091;&#1083;., 3',0),
 (2,'&#1050;&#1086;&#1078;&#1091;&#1093;&#1086;&#1074;&#1089;&#1082;&#1072;&#1103; &#1091;&#1083;., 18',1),
 (3,'&#1040;&#1088;&#1072;&#1083;&#1077;&#1090;&#1086;&#1074;&#1089;&#1082;&#1080;&#1081; &#1087;&#1077;&#1088;., 1',1);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;


--
-- Definition of table `contragents`
--

DROP TABLE IF EXISTS `contragents`;
CREATE TABLE `contragents` (
  `contragent_id` int(11) NOT NULL auto_increment,
  `contragent_name` char(100) NOT NULL default '<unknown>',
  `contragent_active` int(11) NOT NULL default '1',
  `contragent_address` int(11) NOT NULL,
  `contragent_ischild` int(11) NOT NULL default '0',
  PRIMARY KEY  (`contragent_id`),
  KEY `contragent_address` (`contragent_address`),
  CONSTRAINT `FK_contragents_1` FOREIGN KEY (`contragent_address`) REFERENCES `addresses` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contragents`
--

/*!40000 ALTER TABLE `contragents` DISABLE KEYS */;
INSERT INTO `contragents` (`contragent_id`,`contragent_name`,`contragent_active`,`contragent_address`,`contragent_ischild`) VALUES 
 (1,'&#1061;&#1083;&#1077;&#1073;&#1079;&#1072;&#1074;&#1086;&#1076;&#8470;1',0,2,1),
 (2,'&#1061;&#1083;&#1077;&#1073;&#1086;&#1084;&#1072;&#1075;&#1072;&#1079;&#1080;&#1085;',1,1,1),
 (3,'&#1042;&#1077;&#1090;&#1088;&#1103;&#1082;&#8470;3',1,3,0),
 (4,'&#1052;&#1072;&#1075;&#1072;&#1079;&#1080;&#1096;&#1082;&#1072;',1,2,0),
 (5,'&#1055;&#1088;&#1086;&#1082;&#1091;&#1088;&#1072;&#1090;&#1091;&#1088;&#1072;',1,2,0);
/*!40000 ALTER TABLE `contragents` ENABLE KEYS */;


--
-- Definition of table `device_parameters`
--

DROP TABLE IF EXISTS `device_parameters`;
CREATE TABLE `device_parameters` (
  `device_parameter_id` int(11) NOT NULL auto_increment,
  `device_parameter_name` char(100) NOT NULL default '<unknown>',
  `device_parameter_active` int(11) NOT NULL default '1',
  `device_parameter_unit` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `device_parameter_changable` char(10) default NULL,
  PRIMARY KEY  (`device_parameter_id`),
  KEY `device_parameter_unit` (`device_parameter_unit`),
  KEY `device_id` (`device_id`),
  CONSTRAINT `FK_device_parameters_1` FOREIGN KEY (`device_parameter_unit`) REFERENCES `units` (`unit_id`),
  CONSTRAINT `FK_device_parameters_2` FOREIGN KEY (`device_id`) REFERENCES `devices` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `device_parameters`
--

/*!40000 ALTER TABLE `device_parameters` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_parameters` ENABLE KEYS */;


--
-- Definition of table `devices`
--

DROP TABLE IF EXISTS `devices`;
CREATE TABLE `devices` (
  `device_id` int(11) NOT NULL auto_increment,
  `device_active` int(11) NOT NULL default '1',
  `device_name` char(100) NOT NULL default '<unknown>',
  `device_desc` char(250) default NULL,
  `device_recip_id` int(11) NOT NULL,
  PRIMARY KEY  (`device_id`),
  KEY `device_recip_id` (`device_recip_id`),
  CONSTRAINT `FK_devices_1` FOREIGN KEY (`device_recip_id`) REFERENCES `recips` (`recip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `devices`
--

/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;


--
-- Definition of table `measures`
--

DROP TABLE IF EXISTS `measures`;
CREATE TABLE `measures` (
  `measure_id` int(11) NOT NULL auto_increment,
  `measure_value` float NOT NULL,
  `measure_device_parameter` int(11) NOT NULL,
  `measure_time` datetime NOT NULL,
  PRIMARY KEY  (`measure_id`),
  KEY `measure_device_parameter` (`measure_device_parameter`),
  CONSTRAINT `FK_measures_1` FOREIGN KEY (`measure_device_parameter`) REFERENCES `device_parameters` (`device_parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `measures`
--

/*!40000 ALTER TABLE `measures` DISABLE KEYS */;
/*!40000 ALTER TABLE `measures` ENABLE KEYS */;


--
-- Definition of table `money_moves`
--

DROP TABLE IF EXISTS `money_moves`;
CREATE TABLE `money_moves` (
  `money_move_id` int(11) NOT NULL auto_increment,
  `money_move_desc` char(250) default NULL,
  `money_move_amount` float default NULL,
  `money_move_date` datetime default NULL,
  `money_move_destination` int(11) NOT NULL,
  `money_move_source` int(11) NOT NULL,
  `money_move_order` int(11) NOT NULL,
  `money_move_product` int(11) NOT NULL,
  PRIMARY KEY  (`money_move_id`),
  KEY `money_move_destination` (`money_move_destination`),
  KEY `money_move_order` (`money_move_order`),
  KEY `money_move_product` (`money_move_product`),
  CONSTRAINT `FK_money_moves_1` FOREIGN KEY (`money_move_destination`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `FK_money_moves_2` FOREIGN KEY (`money_move_order`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_money_moves_3` FOREIGN KEY (`money_move_product`) REFERENCES `price_list_items` (`price_list_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `money_moves`
--

/*!40000 ALTER TABLE `money_moves` DISABLE KEYS */;
INSERT INTO `money_moves` (`money_move_id`,`money_move_desc`,`money_move_amount`,`money_move_date`,`money_move_destination`,`money_move_source`,`money_move_order`,`money_move_product`) VALUES 
 (1,'',14,'2009-02-06 17:44:15',6,2,2,3),
 (2,'',19000,'1970-01-01 03:00:00',6,2,2,3);
/*!40000 ALTER TABLE `money_moves` ENABLE KEYS */;


--
-- Definition of table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL auto_increment,
  `order_provider` int(11) NOT NULL,
  `order_consumer` int(11) NOT NULL,
  `order_creation_date` datetime NOT NULL,
  `order_active` int(11) NOT NULL default '1',
  `order_done_date` datetime default NULL,
  PRIMARY KEY  (`order_id`),
  KEY `order_consumer` (`order_consumer`),
  CONSTRAINT `FK_orders_1` FOREIGN KEY (`order_consumer`) REFERENCES `contragents` (`contragent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`order_id`,`order_provider`,`order_consumer`,`order_creation_date`,`order_active`,`order_done_date`) VALUES 
 (1,5,2,'3898-03-01 12:44:59',0,'1970-01-01 03:00:00'),
 (2,5,3,'3902-06-06 03:00:00',1,'1970-01-01 03:00:00');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;


--
-- Definition of table `plans`
--

DROP TABLE IF EXISTS `plans`;
CREATE TABLE `plans` (
  `plan_id` int(11) NOT NULL auto_increment,
  `order_id` int(11) NOT NULL,
  `device_id` int(11) NOT NULL,
  `end_date` datetime default NULL,
  PRIMARY KEY  (`plan_id`),
  KEY `order_id` (`order_id`),
  KEY `device_id` (`device_id`),
  CONSTRAINT `FK_plans_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_plans_2` FOREIGN KEY (`device_id`) REFERENCES `devices` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `plans`
--

/*!40000 ALTER TABLE `plans` DISABLE KEYS */;
/*!40000 ALTER TABLE `plans` ENABLE KEYS */;


--
-- Definition of table `price_list_heads`
--

DROP TABLE IF EXISTS `price_list_heads`;
CREATE TABLE `price_list_heads` (
  `price_list_head_id` int(11) NOT NULL auto_increment,
  `price_list_head_date` datetime NOT NULL,
  `price_list_head_comment` char(250) default NULL,
  `price_list_head_contragent` int(11) NOT NULL,
  `price_list_head_active` int(11) NOT NULL default '1',
  PRIMARY KEY  (`price_list_head_id`),
  KEY `price_list_head_contragent` (`price_list_head_contragent`),
  CONSTRAINT `FK_price_lists_1` FOREIGN KEY (`price_list_head_contragent`) REFERENCES `contragents` (`contragent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `price_list_heads`
--

/*!40000 ALTER TABLE `price_list_heads` DISABLE KEYS */;
INSERT INTO `price_list_heads` (`price_list_head_id`,`price_list_head_date`,`price_list_head_comment`,`price_list_head_contragent`,`price_list_head_active`) VALUES 
 (1,'1909-04-03 03:00:00','',5,0),
 (2,'3898-02-02 03:00:00','&#1055;&#1086;&#1089;&#1090;&#1086;&#1103;&#1085;&#1085;&#1099;&#1084; &#1082;&#1083;&#1080;&#1077;&#1085;&#1090;&#1072;&#1084;',5,1);
/*!40000 ALTER TABLE `price_list_heads` ENABLE KEYS */;


--
-- Definition of table `price_list_items`
--

DROP TABLE IF EXISTS `price_list_items`;
CREATE TABLE `price_list_items` (
  `price_list_item_id` int(11) NOT NULL auto_increment,
  `price_list_item_price` float default NULL,
  `price_list_item_head` int(11) NOT NULL,
  `price_list_item_active` int(11) NOT NULL default '1',
  `price_list_product_type` int(11) NOT NULL,
  PRIMARY KEY  (`price_list_item_id`),
  KEY `price_list_item_head` (`price_list_item_head`),
  KEY `price_list_product_type` (`price_list_product_type`),
  CONSTRAINT `FK_price_list_items_1` FOREIGN KEY (`price_list_item_head`) REFERENCES `price_list_heads` (`price_list_head_id`),
  CONSTRAINT `FK_price_list_items_2` FOREIGN KEY (`price_list_product_type`) REFERENCES `product_types` (`product_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `price_list_items`
--

/*!40000 ALTER TABLE `price_list_items` DISABLE KEYS */;
INSERT INTO `price_list_items` (`price_list_item_id`,`price_list_item_price`,`price_list_item_head`,`price_list_item_active`,`price_list_product_type`) VALUES 
 (1,1000,1,0,5),
 (2,0,1,1,4),
 (3,0,2,1,1),
 (4,998,2,1,5),
 (5,14.93,2,1,4);
/*!40000 ALTER TABLE `price_list_items` ENABLE KEYS */;


--
-- Definition of table `product_moves`
--

DROP TABLE IF EXISTS `product_moves`;
CREATE TABLE `product_moves` (
  `product_move_id` int(11) NOT NULL auto_increment,
  `product_move_source` int(11) NOT NULL,
  `product_move_destination` int(11) NOT NULL,
  `product_move_date` datetime default NULL,
  `product_move_money_move` int(11) default NULL,
  PRIMARY KEY  (`product_move_id`),
  KEY `product_move_source` (`product_move_source`),
  KEY `product_move_source_2` (`product_move_source`),
  KEY `product_move_source_3` (`product_move_source`),
  KEY `product_move_money_move` (`product_move_money_move`),
  CONSTRAINT `FK_product_moves_2` FOREIGN KEY (`product_move_source`) REFERENCES `product_warehouses` (`product_warehouse_id`),
  CONSTRAINT `FK_product_moves_3` FOREIGN KEY (`product_move_source`) REFERENCES `product_warehouses` (`product_warehouse_id`),
  CONSTRAINT `FK_product_moves_4` FOREIGN KEY (`product_move_source`) REFERENCES `product_warehouses` (`product_warehouse_id`),
  CONSTRAINT `FK_product_moves_5` FOREIGN KEY (`product_move_money_move`) REFERENCES `money_moves` (`money_move_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_moves`
--

/*!40000 ALTER TABLE `product_moves` DISABLE KEYS */;
INSERT INTO `product_moves` (`product_move_id`,`product_move_source`,`product_move_destination`,`product_move_date`,`product_move_money_move`) VALUES 
 (1,6,2,'2009-02-06 00:00:00',1),
 (2,1,2,'2009-02-06 17:49:35',2),
 (3,6,3,'2009-02-06 00:00:00',1),
 (4,2,1,'2009-02-06 17:42:16',2),
 (5,2,2,'2009-02-06 17:47:47',2),
 (6,2,2,'2009-02-06 17:49:18',2);
/*!40000 ALTER TABLE `product_moves` ENABLE KEYS */;


--
-- Definition of table `product_types`
--

DROP TABLE IF EXISTS `product_types`;
CREATE TABLE `product_types` (
  `product_type_id` int(11) NOT NULL auto_increment,
  `product_type_name` char(100) NOT NULL default '<unknown>',
  `product_type_unit` int(11) NOT NULL,
  `product_type_active` int(11) NOT NULL default '1',
  PRIMARY KEY  (`product_type_id`),
  KEY `product_type_unit` (`product_type_unit`),
  CONSTRAINT `FK_product_types_1` FOREIGN KEY (`product_type_unit`) REFERENCES `units` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_types`
--

/*!40000 ALTER TABLE `product_types` DISABLE KEYS */;
INSERT INTO `product_types` (`product_type_id`,`product_type_name`,`product_type_unit`,`product_type_active`) VALUES 
 (1,'&#1042;&#1086;&#1076;&#1072;',3,0),
 (2,'&#1052;&#1091;&#1082;&#1072;',1,1),
 (4,'&#1063;&#1077;&#1088;&#1074;&#1103;&#1082;&#1080;',2,1),
 (5,'&#1048;&#1085;&#1076;&#1091;&#1083;&#1100;&#1075;&#1077;&#1085;&#1094;&#1080;&#1103;',4,1);
/*!40000 ALTER TABLE `product_types` ENABLE KEYS */;


--
-- Definition of table `product_warehouses`
--

DROP TABLE IF EXISTS `product_warehouses`;
CREATE TABLE `product_warehouses` (
  `product_warehouse_id` int(11) NOT NULL auto_increment,
  `product_warehouse_name` char(100) NOT NULL default '<unknown>',
  `product_warehouse_address` int(11) NOT NULL,
  `product_warehouse_owner` int(11) NOT NULL,
  `product_warehouse_active` char(10) default NULL,
  PRIMARY KEY  (`product_warehouse_id`),
  KEY `product_warehouse_address` (`product_warehouse_address`),
  KEY `product_warehouse_owner` (`product_warehouse_owner`),
  CONSTRAINT `FK_product_warehouses_1` FOREIGN KEY (`product_warehouse_address`) REFERENCES `addresses` (`address_id`),
  CONSTRAINT `FK_product_warehouses_2` FOREIGN KEY (`product_warehouse_owner`) REFERENCES `contragents` (`contragent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_warehouses`
--

/*!40000 ALTER TABLE `product_warehouses` DISABLE KEYS */;
INSERT INTO `product_warehouses` (`product_warehouse_id`,`product_warehouse_name`,`product_warehouse_address`,`product_warehouse_owner`,`product_warehouse_active`) VALUES 
 (1,'&#1052;&#1091;&#1082;&#1086;&#1089;&#1082;&#1083;&#1072;&#1076;',2,3,'1'),
 (2,'&#1052;&#1091;&#1082;&#1086;&#1089;&#1082;&#1083;&#1072;&#1076;',2,1,'1'),
 (3,'&#1055;&#1088;&#1086;&#1076;&#1091;&#1082;&#1090;&#1086;&#1089;&#1082;&#1083;&#1072;&#1076;',2,3,'1'),
 (4,'&#1058;&#1102;&#1088;&#1100;&#1084;&#1072;',2,5,'1'),
 (6,'&#1053;&#1072;&#1075;&#1088;&#1072;&#1073;&#1083;&#1077;&#1085;&#1085;&#1086;&#1077;',2,5,'0'),
 (7,'&#1044;&#1083;&#1103; &#1085;&#1072;&#1088;&#1082;&#1086;&#1090;&#1080;&#1082;&#1086;&#1074;',1,5,'0');
/*!40000 ALTER TABLE `product_warehouses` ENABLE KEYS */;


--
-- Definition of table `recip_effects`
--

DROP TABLE IF EXISTS `recip_effects`;
CREATE TABLE `recip_effects` (
  `recip_effect_id` int(11) NOT NULL auto_increment,
  `recip_effect_product_amount` float NOT NULL,
  `recip_effect_product_type` int(11) NOT NULL,
  `recip_effect_result_formula` char(250) NOT NULL,
  `recip_effect_recip` int(11) NOT NULL,
  `recip_effect_is_consumed` int(11) NOT NULL,
  PRIMARY KEY  (`recip_effect_id`),
  KEY `recip_effect_product_type` (`recip_effect_product_type`),
  KEY `recip_effect_recip` (`recip_effect_recip`),
  CONSTRAINT `FK_recip_results_1` FOREIGN KEY (`recip_effect_product_type`) REFERENCES `product_types` (`product_type_id`),
  CONSTRAINT `FK_recip_effects_2` FOREIGN KEY (`recip_effect_recip`) REFERENCES `recips` (`recip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recip_effects`
--

/*!40000 ALTER TABLE `recip_effects` DISABLE KEYS */;
/*!40000 ALTER TABLE `recip_effects` ENABLE KEYS */;


--
-- Definition of table `recip_parameters`
--

DROP TABLE IF EXISTS `recip_parameters`;
CREATE TABLE `recip_parameters` (
  `recip_parameter_id` int(11) NOT NULL,
  `device_parameter_id` int(11) NOT NULL,
  KEY `recip_parameter_id` (`recip_parameter_id`),
  KEY `device_parameter_id` (`device_parameter_id`),
  CONSTRAINT `FK_recip_parameters_1` FOREIGN KEY (`recip_parameter_id`) REFERENCES `recips` (`recip_id`),
  CONSTRAINT `FK_recip_parameters_2` FOREIGN KEY (`device_parameter_id`) REFERENCES `device_parameters` (`device_parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recip_parameters`
--

/*!40000 ALTER TABLE `recip_parameters` DISABLE KEYS */;
/*!40000 ALTER TABLE `recip_parameters` ENABLE KEYS */;


--
-- Definition of table `recips`
--

DROP TABLE IF EXISTS `recips`;
CREATE TABLE `recips` (
  `recip_id` int(11) NOT NULL auto_increment,
  `recip_name` char(100) NOT NULL default '<unknown>',
  `recip_formula` char(250) default NULL,
  `recip_active` int(11) NOT NULL default '1',
  PRIMARY KEY  (`recip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recips`
--

/*!40000 ALTER TABLE `recips` DISABLE KEYS */;
/*!40000 ALTER TABLE `recips` ENABLE KEYS */;


--
-- Definition of table `units`
--

DROP TABLE IF EXISTS `units`;
CREATE TABLE `units` (
  `unit_id` int(11) NOT NULL auto_increment,
  `unit_name` char(100) NOT NULL default '<unknown>',
  `unit_active` int(11) NOT NULL default '1',
  PRIMARY KEY  (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `units`
--

/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` (`unit_id`,`unit_name`,`unit_active`) VALUES 
 (1,'&#1082;&#1075;.',0),
 (2,'&#1084;&#1084;.',1),
 (3,'&#1083;&#1080;&#1090;&#1088;.',1),
 (4,'&#1096;&#1090;.',1);
/*!40000 ALTER TABLE `units` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
