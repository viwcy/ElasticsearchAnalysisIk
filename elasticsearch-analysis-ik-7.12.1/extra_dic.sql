/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : extra_dic

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 26/01/2022 17:03:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_keyword
-- ----------------------------
DROP TABLE IF EXISTS `sys_keyword`;
CREATE TABLE `sys_keyword`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `word` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '词语',
  `last_update` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_keyword
-- ----------------------------
INSERT INTO `sys_keyword` VALUES (1, '小正太', '2022-01-26 13:50:53');
INSERT INTO `sys_keyword` VALUES (2, '1懂', '2022-01-26 13:50:53');
INSERT INTO `sys_keyword` VALUES (3, '元宇宙', '2022-01-26 15:34:20');
INSERT INTO `sys_keyword` VALUES (4, '天青色', '2022-01-26 16:43:50');

-- ----------------------------
-- Table structure for sys_stopword
-- ----------------------------
DROP TABLE IF EXISTS `sys_stopword`;
CREATE TABLE `sys_stopword`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `stop_word` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '停用词',
  `last_update` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_stopword
-- ----------------------------
INSERT INTO `sys_stopword` VALUES (2, '1', '2022-01-26 15:33:11');
INSERT INTO `sys_stopword` VALUES (3, '懂', '2022-01-26 15:47:32');

SET FOREIGN_KEY_CHECKS = 1;
