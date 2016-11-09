/*
Navicat MySQL Data Transfer

Source Server         : csbp
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : itss

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2016-11-08 11:48:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tmanageruser`
-- ----------------------------
DROP TABLE IF EXISTS `tmanageruser`;
CREATE TABLE `tmanageruser` (
  `salt` varchar(100) DEFAULT NULL,
  `UserID` varchar(100) NOT NULL,
  `UserName` varchar(100) DEFAULT NULL,
  `Password` varchar(200) DEFAULT NULL,
  `UserType` int(11) DEFAULT '0' COMMENT '1管理员2经销商3安装工4客户',
  `IsStoped` tinyint(1) DEFAULT '0',
  `Note` varchar(2000) DEFAULT NULL,
  `Phone` varchar(100) DEFAULT NULL,
  `AvatarPath` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tmanageruser
-- ----------------------------
INSERT INTO `tmanageruser` VALUES ('8449a736b311440a', 'wj', 'WJ', '9d29dbed550fc22f41fcc607a12cd45938ad930d', '0', '0', null, null, null);
INSERT INTO `tmanageruser` VALUES ('8449a736b311440a', 'admin', 'ADMIN', '9d29dbed550fc22f41fcc607a12cd45938ad930d', '0', '0', null, null, null);
