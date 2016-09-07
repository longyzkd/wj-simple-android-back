

-- ----------------------------
-- Table structure for tmanageruser
-- ----------------------------

CREATE TABLE tmanageruser (
  UserID varchar(100) NOT NULL,
  UserName varchar(100) DEFAULT NULL,
  Password varchar(200) DEFAULT NULL,
  UserType int(11) DEFAULT '0' COMMENT '1管理员2经销商3安装工4客户',
  IsStoped tinyint(1) DEFAULT '0',
  Note varchar(2000) DEFAULT NULL,
  Phone varchar(100) DEFAULT NULL,
  AvatarPath varchar(100) DEFAULT NULL,
  PRIMARY KEY (UserID)
) ;
