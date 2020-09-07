/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : book_manage

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2020-08-24 16:51:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bm_book
-- ----------------------------
DROP TABLE IF EXISTS `bm_book`;
CREATE TABLE `bm_book` (
  `book_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图书ID',
  `name` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图书名称',
  `sub_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图书名称（副）',
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '作者',
  `publishing` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '出版社',
  `published_date` datetime NOT NULL COMMENT '出版日期',
  `summary` text CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `remarks` varchar(10000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `classify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类别ID',
  `classify` varchar(25) NOT NULL COMMENT '类别',
  `series` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '辑',
  `series_order` int(11) NOT NULL COMMENT '辑次序',
  `thumb_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '缩略图',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '城市',
  `district` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区/县',
  `position` varchar(500) DEFAULT NULL COMMENT '存放位置',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '录入者ID',
  `create_time` datetime NOT NULL COMMENT '保存时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书表';

-- ----------------------------
-- Table structure for bm_classify
-- ----------------------------
DROP TABLE IF EXISTS `bm_classify`;
CREATE TABLE `bm_classify` (
  `classify_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `classify` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '一级分类',
  `sub_classify` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级分类',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`classify_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文史资料分类表';

-- ----------------------------
-- Table structure for bm_content
-- ----------------------------
DROP TABLE IF EXISTS `bm_content`;
CREATE TABLE `bm_content` (
  `content_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录名称',
  `up_content_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上级目录ID（-1为根目录）',
  `level` int(10) NOT NULL COMMENT '层级',
  `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '作者',
  `start_page` int(100) NOT NULL COMMENT '起始页',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '描述',
  `book_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书ID',
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书目录表';

-- ----------------------------
-- Table structure for bm_user
-- ----------------------------
DROP TABLE IF EXISTS `bm_user`;
CREATE TABLE `bm_user` (
  `user_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `user_pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户姓名',
  `type_id` varchar(2) NOT NULL COMMENT '类型ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of bm_user
-- ----------------------------
INSERT INTO `bm_user` VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', '1', '2018-09-29 00:00:00', '2020-08-18 09:41:35');

-- ----------------------------
-- Table structure for bm_user_type
-- ----------------------------
DROP TABLE IF EXISTS `bm_user_type`;
CREATE TABLE `bm_user_type` (
  `type_id` varchar(2) NOT NULL COMMENT '类型ID',
  `type_name` varchar(20) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户类型表';

-- ----------------------------
-- Records of bm_user_type
-- ----------------------------
INSERT INTO `bm_user_type` VALUES ('0', '普通用户');
INSERT INTO `bm_user_type` VALUES ('1', '系统管理员');
SET FOREIGN_KEY_CHECKS=1;
