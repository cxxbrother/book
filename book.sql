/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : book

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2016-03-30 20:52:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book_info`
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '书本id',
  `ISBN` varchar(30) DEFAULT NULL COMMENT '书号',
  `book_name` varchar(70) DEFAULT NULL COMMENT '书名',
  `type` varchar(70) DEFAULT NULL COMMENT '类型',
  `author` varchar(30) DEFAULT NULL COMMENT '作者',
  `publisher` varchar(30) DEFAULT NULL COMMENT '出版社',
  `translator` varchar(30) DEFAULT NULL COMMENT '翻译者',
  `publish_time` date DEFAULT NULL COMMENT '出版日期',
  `price` float(8,2) DEFAULT NULL COMMENT '价格',
  `in_time` datetime DEFAULT NULL COMMENT '加入时间',
  `is_delete` int(1) unsigned zerofill DEFAULT '0' COMMENT '是否已被移除【0否 1是】',
  `operator_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `has_number` int(10) DEFAULT NULL COMMENT '目前剩余量【未被借走】',
  `total_number` int(10) DEFAULT '0' COMMENT '书总量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `book_info_id` (`id`) USING BTREE,
  KEY `book_info_name` (`book_name`) USING BTREE,
  KEY `book_info_isbn` (`ISBN`) USING BTREE,
  KEY `book_info_author` (`author`) USING BTREE,
  KEY `book_info_publisher` (`publisher`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='书本信息表';

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES ('1', '123', '海洋之心', '文学', '阿基米德', '清华大学出版社', 'cxx', '2015-11-11', '11.00', '2016-01-08 20:29:33', '0', '1', '100', '100');
INSERT INTO `book_info` VALUES ('2', '452', 'C#程序设计', '编程', '***', '北京大学出版社', 'cxx', '2001-01-01', '50.00', '2016-01-09 20:38:42', '0', '1', '9', '10');
INSERT INTO `book_info` VALUES ('6', '45878', 'Windows程序设计', '编程', '小强', '清华大学出版社', '幽默感', '2016-01-09', '49.00', '2016-01-09 21:29:21', '0', '2', '10', '10');
INSERT INTO `book_info` VALUES ('7', '452187', '广工的怒火', '文学类', '陈理锐', '广工西区', 'cxx', '2016-01-01', '99.00', '2016-01-10 12:30:43', '0', '1', '50', '50');
INSERT INTO `book_info` VALUES ('8', '45178', '打炮的生涯', '游戏', '陈国兴', '广工西区', 'cxx', '2015-10-09', '9.00', '2016-01-10 13:24:33', '0', '1', '9', '10');
INSERT INTO `book_info` VALUES ('9', '415318', '床上的生活', '生活', '陈礼锐', '广工西区', '陈绪雄', '2016-01-01', '10.00', '2016-01-10 13:25:27', '0', '1', '5', '5');
INSERT INTO `book_info` VALUES ('10', '487655', '论ss的技术', '生理', '邓伯亮', '广工东区', '陈国兴', '2016-01-11', '1.50', '2016-01-10 13:26:58', '0', '1', '1', '2');
INSERT INTO `book_info` VALUES ('12', '528774', '数据库设计', '计算机', 'cxx', '广东工业大学', 'cxx', '2016-01-10', '19.50', '2016-01-10 21:20:57', '0', '1', '21', '20');
INSERT INTO `book_info` VALUES ('13', '12323', '测试', 'dasdas', 'sdaasd', 'fsdf', 'ccx', '2015-01-01', '22.00', '2016-01-11 15:16:33', '1', '1', '11', '11');

-- ----------------------------
-- Table structure for `borrow`
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '借书条目id',
  `reader_id` int(10) unsigned DEFAULT NULL COMMENT '借书的读者id',
  `book_id` int(10) DEFAULT NULL COMMENT '书本id',
  `borrow_time` datetime DEFAULT NULL COMMENT '借书时间',
  `deadline` datetime DEFAULT NULL COMMENT '应还书时间',
  `back_time` datetime DEFAULT NULL COMMENT '还书时间',
  `operator_id` int(10) DEFAULT NULL COMMENT '操作人id',
  `is_back` int(1) unsigned zerofill DEFAULT '0' COMMENT '是否返回【0否 1是】',
  PRIMARY KEY (`id`),
  KEY `book_info_id` (`book_id`) USING BTREE,
  KEY `user_info_id` (`reader_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('1', '1', '1', '2016-01-10 16:36:02', '2007-12-22 00:00:00', '2007-12-22 00:00:00', '1', '1');
INSERT INTO `borrow` VALUES ('2', '1', '2', '2007-11-26 00:00:00', '2007-12-22 00:00:00', '2007-12-26 00:00:00', '1', '1');
INSERT INTO `borrow` VALUES ('3', '1', '1', '2007-11-26 00:00:00', '2007-12-22 00:00:00', '2007-12-26 00:00:00', '1', '1');
INSERT INTO `borrow` VALUES ('4', '3', '6', '2007-12-29 00:00:00', '2007-12-22 00:00:00', '2007-01-08 00:00:00', '1', '1');
INSERT INTO `borrow` VALUES ('5', '3', '1', '2007-12-29 00:00:00', '2007-12-22 00:00:00', '2008-01-28 00:00:00', '1', '1');
INSERT INTO `borrow` VALUES ('6', '3', '2', '2007-12-29 00:00:00', '2007-12-22 00:00:00', '2016-01-10 17:34:05', '1', '1');
INSERT INTO `borrow` VALUES ('7', '2', '9', '2016-01-10 16:36:32', '2016-03-10 14:56:28', '2016-01-10 17:21:23', '1', '1');
INSERT INTO `borrow` VALUES ('8', '3', '10', '2016-01-10 15:06:41', '2016-03-10 15:06:41', '2016-01-10 17:21:43', '1', '1');
INSERT INTO `borrow` VALUES ('9', '3', '2', '2015-10-10 15:09:05', '2015-12-10 15:09:05', '2016-01-10 17:44:56', '1', '1');
INSERT INTO `borrow` VALUES ('10', '8', '8', '2015-10-10 21:09:30', '2016-12-10 21:09:30', '2016-01-10 21:44:20', '1', '1');
INSERT INTO `borrow` VALUES ('11', '8', '12', '2015-10-10 21:09:30', '2015-12-10 21:09:30', '2016-01-10 21:50:08', '1', '1');
INSERT INTO `borrow` VALUES ('12', '8', '6', '2016-01-11 09:07:18', '2016-01-01 09:07:18', '2016-01-11 15:20:34', '1', '1');
INSERT INTO `borrow` VALUES ('13', '8', '1', '2016-01-11 15:18:07', '2016-03-11 15:18:07', '2016-01-11 15:19:00', '1', '0');

-- ----------------------------
-- Table structure for `function`
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id，自增',
  `function_name` varchar(16) NOT NULL COMMENT '功能名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='管理后台功能权限表';

-- ----------------------------
-- Records of function
-- ----------------------------
INSERT INTO `function` VALUES ('1', '人员信息管理', 'portal:user:manage', '2015-09-25 17:57:02', '2016-01-11 00:10:17');
INSERT INTO `function` VALUES ('2', '图书信息管理', 'book:manage', '2016-01-11 00:10:50', '2016-01-11 00:11:03');
INSERT INTO `function` VALUES ('3', '图书借阅管理', 'borrow:manage', '2016-01-11 00:10:50', '2016-01-11 00:11:47');
INSERT INTO `function` VALUES ('4', '还书记录管理', 'back:manage', '2016-01-11 00:10:50', '2016-01-11 00:11:45');
INSERT INTO `function` VALUES ('5', '用户类型与罚金设置', 'user_type_fine:manage', '2016-01-11 00:12:16', '2016-01-11 00:12:19');

-- ----------------------------
-- Table structure for `giveback`
-- ----------------------------
DROP TABLE IF EXISTS `giveback`;
CREATE TABLE `giveback` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '还书条目id',
  `borrow_id` int(10) DEFAULT NULL COMMENT '读者id',
  `is_overdue` int(1) unsigned zerofill DEFAULT '0' COMMENT '是否逾期【0否 1是】',
  `is_lost` int(1) DEFAULT NULL COMMENT '是否丢失【0否 1是】',
  `need_feed_money` float(8,2) unsigned zerofill DEFAULT '00000.00' COMMENT '逾期或丢失  应还金额',
  `operator_id` int(10) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of giveback
-- ----------------------------
INSERT INTO `giveback` VALUES ('1', '1', '1', '0', '00001.00', '1');
INSERT INTO `giveback` VALUES ('2', '3', '0', '0', '00000.00', '1');
INSERT INTO `giveback` VALUES ('3', '2', '0', '0', '00000.00', '1');
INSERT INTO `giveback` VALUES ('4', '1', '0', '0', '00000.00', '1');
INSERT INTO `giveback` VALUES ('5', '7', '0', '0', '00000.00', '1');
INSERT INTO `giveback` VALUES ('6', '7', '0', '0', '00000.00', '1');
INSERT INTO `giveback` VALUES ('7', '8', '0', '0', '00000.00', '1');
INSERT INTO `giveback` VALUES ('8', '6', '0', '1', '00025.00', '1');
INSERT INTO `giveback` VALUES ('14', '10', '0', '1', '00003.60', '1');
INSERT INTO `giveback` VALUES ('16', '11', '1', '0', '00003.10', '1');
INSERT INTO `giveback` VALUES ('17', '13', '0', '1', '00004.40', '1');
INSERT INTO `giveback` VALUES ('18', '12', '1', '0', '00001.00', '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id，自增',
  `role_name` varchar(16) NOT NULL COMMENT '角色名称',
  `role_code` varchar(16) NOT NULL COMMENT '角色code',
  `can_borrow_number` int(5) unsigned zerofill DEFAULT '00000' COMMENT '可借书数目',
  `overdue_fine` float(4,2) unsigned zerofill DEFAULT '0.00' COMMENT '逾期罚款（该数值*天数）',
  `is_delete` int(1) unsigned zerofill DEFAULT '0' COMMENT '是否被删除【0否 1是】',
  `lost_fine` float(4,2) unsigned zerofill DEFAULT '0.00' COMMENT '遗失罚款（原价*该数值）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='管理后台角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', 'administrator', '01000', '0.10', '0', '0.60', '2015-09-25 17:56:00', '2016-01-10 01:20:30');
INSERT INTO `role` VALUES ('2', '普通管理员', 'general_manager', '01000', '0.10', '0', '0.30', '2015-09-25 17:55:53', '2016-01-10 01:20:32');
INSERT INTO `role` VALUES ('3', '本科生', 'undergraduate', '00010', '0.10', '0', '0.50', '2016-01-08 18:13:56', '2016-01-10 01:20:35');
INSERT INTO `role` VALUES ('4', '研究生', 'graduate_student', '00020', '0.10', '0', '0.40', '2016-01-08 18:14:52', '2016-01-10 01:20:37');
INSERT INTO `role` VALUES ('5', '博士生', 'doctoral_student', '00030', '0.20', '0', '0.20', '2016-01-08 18:15:34', '2016-01-10 19:10:58');
INSERT INTO `role` VALUES ('6', '博士后', 'postdoctor ', '00040', '0.10', '0', '0.40', '2016-01-08 18:16:13', '2016-01-10 01:20:46');
INSERT INTO `role` VALUES ('7', '讲师', 'teacher', '00050', '0.10', '0', '0.40', '2016-01-08 18:16:55', '2016-01-10 01:20:49');
INSERT INTO `role` VALUES ('8', '教师', 'teachers', '00100', '0.01', '1', '0.10', '2016-01-10 19:43:05', '2016-01-10 19:43:09');
INSERT INTO `role` VALUES ('9', '群众', 'public', '00010', '0.10', '0', '0.20', '2016-01-11 15:21:19', '2016-01-11 15:21:18');

-- ----------------------------
-- Table structure for `role_function`
-- ----------------------------
DROP TABLE IF EXISTS `role_function`;
CREATE TABLE `role_function` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `function_id` int(11) NOT NULL COMMENT '功能权限id',
  PRIMARY KEY (`role_id`,`function_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='管理后台角色-功能权限表';

-- ----------------------------
-- Records of role_function
-- ----------------------------
INSERT INTO `role_function` VALUES ('1', '1');
INSERT INTO `role_function` VALUES ('1', '2');
INSERT INTO `role_function` VALUES ('1', '3');
INSERT INTO `role_function` VALUES ('1', '4');
INSERT INTO `role_function` VALUES ('1', '5');
INSERT INTO `role_function` VALUES ('2', '2');
INSERT INTO `role_function` VALUES ('2', '3');
INSERT INTO `role_function` VALUES ('2', '4');

-- ----------------------------
-- Table structure for `sysconfig`
-- ----------------------------
DROP TABLE IF EXISTS `sysconfig`;
CREATE TABLE `sysconfig` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `servercode` varchar(24) NOT NULL COMMENT '服务器标识(公共标识:com)',
  `ckey` varchar(64) NOT NULL COMMENT '键',
  `cvalue` varchar(256) DEFAULT NULL COMMENT '值',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_servercode_ckey` (`servercode`,`ckey`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='系统配置项';

-- ----------------------------
-- Records of sysconfig
-- ----------------------------
INSERT INTO `sysconfig` VALUES ('1', 'com', 'LOG_SWITCH', '1', '打印访问日志的开关', '2014-12-13 16:00:28', '2015-06-28 09:05:43');
INSERT INTO `sysconfig` VALUES ('2', 'com', 'FILE_DOMAIN', 'http://112.74.27.147/file/', '静态文件的域名', '2014-12-20 23:27:15', '2015-08-07 12:08:58');
INSERT INTO `sysconfig` VALUES ('3', 'com', 'PROTAL_DOMAIN', 'http://112.74.27.147/matheasy-portal/', 'portal域名', '2015-05-12 21:55:25', '2015-12-31 21:55:14');
INSERT INTO `sysconfig` VALUES ('4', 'com', 'FILE_PATH', '/web/rising/upload/file/', '文件存储路径', '2015-05-12 21:55:22', '2015-09-19 10:35:04');
INSERT INTO `sysconfig` VALUES ('8', 'com', 'BAIDU_PUSH_APIKEY_GUIDE_IOS', 'b41glvgefygzMcsoBpqbVXOx', '', '2015-08-29 14:38:48', '2015-09-28 11:19:53');
INSERT INTO `sysconfig` VALUES ('9', 'com', 'BAIDU_PUSH_SECRETKEY_GUIDE_IOS', 'oaNwuO18TATbjuHhhZQlAIEbeI2tKFFk', '', '2015-08-29 14:41:05', '2015-09-28 11:20:03');
INSERT INTO `sysconfig` VALUES ('5', 'com', 'REDIS_SERVERS', '127.0.0.1:6379', 'redis服务', '2015-05-12 21:55:19', '2015-08-07 12:10:40');
INSERT INTO `sysconfig` VALUES ('6', 'com', 'INTERFACE_DOMAIN', 'http://112.74.27.147/matheasy/', '接口服务域名', '2015-05-14 18:25:51', '2015-12-31 21:55:01');
INSERT INTO `sysconfig` VALUES ('10', 'com', 'BAIDU_PUSH_APIKEY_GUIDE_ANDROID', 'ww5clxYh6Detrc5COPdyzuqu', '', '2015-09-06 11:08:24', '2015-09-28 11:20:19');
INSERT INTO `sysconfig` VALUES ('11', 'com', 'BAIDU_PUSH_SECRETKEY_GUIDE_ANDROID', 'Z2Xw60zw9nefOGBmNkKIuWrW1TzhGRxM', '', '2015-09-06 11:08:23', '2015-09-28 11:20:28');
INSERT INTO `sysconfig` VALUES ('12', 'com', 'BAIDU_PUSH_IOS_DEPLOY_STATUS', '2', '', '2015-09-06 11:08:22', '2015-10-19 10:38:02');
INSERT INTO `sysconfig` VALUES ('15', 'com', 'PINGXX_APIKEY', 'sk_test_XbDmr9u1yPiL8m5mjDz58OiL', '', '2015-09-19 16:26:43', '2015-09-24 09:28:45');
INSERT INTO `sysconfig` VALUES ('16', 'com', 'PINGXX_APPID', 'app_r5OCm9XfzHmTbDm5', '', '2015-09-19 16:26:43', '2015-09-24 09:28:16');
INSERT INTO `sysconfig` VALUES ('17', 'com', 'PINGXX_MAXPAYMIN', '5', '', '2015-09-19 16:26:43', '2015-09-30 10:19:49');
INSERT INTO `sysconfig` VALUES ('19', 'com', 'SERVICE_CHARGE_START_PRICE_PER_HOUR', '10600', '前3小时的服务费单价（单位：分）', '2015-09-27 17:28:42', '2015-09-29 14:51:04');
INSERT INTO `sysconfig` VALUES ('20', 'com', 'BAIDU_PUSH_APIKEY_CUSTOMER_IOS', 'HlBm46GnkoLhKoG0cmuYMGpl', '', '2015-08-29 14:38:48', '2015-09-28 11:28:18');
INSERT INTO `sysconfig` VALUES ('21', 'com', 'BAIDU_PUSH_SECRETKEY_CUSTOMER_IOS', '0OqviXQIURQZmGiIFguc4vBXZhA91NGH', '', '2015-08-29 14:41:05', '2015-09-28 11:28:21');
INSERT INTO `sysconfig` VALUES ('22', 'com', 'BAIDU_PUSH_APIKEY_CUSTOMER_ANDROID', 'HgmUaIywMXCS69nkzDl7iClD', '', '2015-09-06 11:08:24', '2015-09-28 11:48:38');
INSERT INTO `sysconfig` VALUES ('23', 'com', 'BAIDU_PUSH_SECRETKEY_CUSTOMER_ANDROID', '5DwemL4D6wfpMViqVQUQGgdw9ElTjyQD', '', '2015-09-06 11:08:23', '2015-09-28 11:48:49');

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id，自增',
  `account` varchar(16) NOT NULL COMMENT '账号',
  `passwd` varchar(32) NOT NULL COMMENT '密码，MD5加密',
  `user_name` varchar(16) NOT NULL COMMENT '姓名',
  `library_card` varchar(18) NOT NULL COMMENT '管理员证件或读者证件',
  `idcard` varchar(18) NOT NULL COMMENT '身份证号',
  `is_delete` int(1) unsigned zerofill DEFAULT '0' COMMENT '是否已被删除【0否,1是】',
  `has_borrow_number` int(5) unsigned zerofill DEFAULT '00000' COMMENT '已借书数量',
  `sex` int(1) NOT NULL COMMENT '性别【1男 2女】',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_portal_user_account` (`account`),
  UNIQUE KEY `un_portal_user_library_card` (`library_card`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '4252452', '45245245', '0', '00004', '1', '2015-09-25 18:01:25', '2016-01-10 21:04:12');
INSERT INTO `user_info` VALUES ('2', 'manger', 'e10adc3949ba59abbe56e057f20f883e', '普通管理员', '4524524', '1525452', '0', '00005', '1', '2015-09-25 18:05:09', '2016-01-10 17:21:23');
INSERT INTO `user_info` VALUES ('3', '3113006505', 'e10adc3949ba59abbe56e057f20f883e', '陈绪雄', '123456', '123456', '0', '00010', '1', '2016-01-09 22:41:42', '2016-01-10 21:04:27');
INSERT INTO `user_info` VALUES ('6', '3113006506', 'e10adc3949ba59abbe56e057f20f883e', '邓柏林', '789456', '1541556', '0', '00000', '1', '2016-01-10 15:16:01', '2016-01-10 18:08:20');
INSERT INTO `user_info` VALUES ('7', '3113006503', 'e10adc3949ba59abbe56e057f20f883e', '陈礼锐', '444521', '445281199411165412', '0', '00000', '2', '2016-01-10 20:09:16', '2016-01-10 20:09:43');
INSERT INTO `user_info` VALUES ('8', '3113006504', '25f9e794323b453885f5181f1b624d0b', '陈国兴', '123456789', '123456789', '0', '00000', '2', '2016-01-10 20:11:50', '2016-01-11 15:20:34');
INSERT INTO `user_info` VALUES ('9', '3113004544', 'e10adc3949ba59abbe56e057f20f883e', '广工人', '445281145', '445281199444444', '0', '00000', '1', '2016-01-10 20:32:15', '2016-01-10 20:32:15');
INSERT INTO `user_info` VALUES ('10', '1234', '202cb962ac59075b964b07152d234b70', 'hhhh', '879', '4451223', '0', '00000', '1', '2016-01-11 15:21:59', '2016-01-11 15:21:59');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='管理后台用户角色表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '2015-09-25 18:05:36', '2015-09-25 18:05:45');
INSERT INTO `user_role` VALUES ('2', '2', '2015-09-25 18:06:04', '2015-09-25 18:06:06');
INSERT INTO `user_role` VALUES ('1', '2', '2016-01-08 20:56:28', '2016-01-09 20:56:32');
INSERT INTO `user_role` VALUES ('6', '3', '2016-01-10 15:16:01', '2016-01-10 15:16:01');
INSERT INTO `user_role` VALUES ('3', '3', '2016-01-12 01:26:10', '2016-01-10 01:26:19');
INSERT INTO `user_role` VALUES ('7', '5', '2016-01-10 20:09:16', '2016-01-10 20:09:16');
INSERT INTO `user_role` VALUES ('8', '6', '2016-01-10 20:11:50', '2016-01-10 20:11:49');
INSERT INTO `user_role` VALUES ('9', '4', '2016-01-10 20:32:15', '2016-01-10 20:32:15');
INSERT INTO `user_role` VALUES ('10', '9', '2016-01-11 15:21:59', '2016-01-11 15:21:59');
