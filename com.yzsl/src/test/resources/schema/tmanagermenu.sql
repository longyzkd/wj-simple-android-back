
CREATE TABLE tmanagermenu (
  MenuID varchar(100) NOT NULL,
  MenuName varchar(400) DEFAULT NULL,
  IconURL varchar(1000) DEFAULT NULL,
  FunURL varchar(1000) DEFAULT NULL,
  FMenuID varchar(1000) DEFAULT NULL,
  FunID varchar(100) DEFAULT NULL,
  OrderID int(11) DEFAULT NULL,
  Note varchar(2000) DEFAULT NULL,
  PRIMARY KEY (MenuID)
) ;

-- ----------------------------
-- Records of tmanagermenu
-- ----------------------------
INSERT INTO tmanagermenu VALUES ('01', '基础信息', 'menu_setting', null, '', '01', '1', null);
INSERT INTO tmanagermenu VALUES ('0101', '用户管理', 'jslib/ExtJs/resources/themes/icons/grid.png', 'area/toArea.do', '01', '0101', '101', null);
INSERT INTO tmanagermenu VALUES ('0102', '水闸管理', 'jslib/ExtJs/resources/themes/icons/book_open.png', 'area/toDicData.do', '01', '0102', '102', null);

