/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80100
Source Host           : localhost:3306
Source Database       : y_stand

Target Server Type    : MYSQL
Target Server Version : 80100
File Encoding         : 65001

Date: 2024-05-25 09:37:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for coin
-- ----------------------------
DROP TABLE IF EXISTS `coin`;
CREATE TABLE `coin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `video_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `coin_video_id` (`video_id`),
  KEY `coin_user_id` (`user_id`),
  CONSTRAINT `coin_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `coin_video_id` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of coin
-- ----------------------------
INSERT INTO `coin` VALUES ('2', '2', '2', '2024-05-21 14:38:05');
INSERT INTO `coin` VALUES ('3', '8', '2', '2024-05-21 14:40:02');
INSERT INTO `coin` VALUES ('7', '19', '13', '2024-05-23 16:00:44');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `video_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `collect_video_id` (`video_id`),
  KEY `collect_user_id` (`user_id`),
  CONSTRAINT `collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `collect_video_id` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('2', '1', '2', '2024-05-23 15:29:51');
INSERT INTO `collect` VALUES ('3', '19', '13', '2024-05-23 16:00:45');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `video_id` bigint NOT NULL,
  `content` text NOT NULL COMMENT '内容',
  `user_id` int NOT NULL COMMENT '评论用户id',
  `like_count` int DEFAULT NULL COMMENT '点赞次数',
  `root_id` bigint DEFAULT NULL COMMENT '顶级评论id',
  `to_id` bigint DEFAULT NULL COMMENT '回复目标评论id',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_Delete` bit(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `comment_video_id` (`video_id`),
  KEY `comment_user_id` (`user_id`),
  CONSTRAINT `comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comment_video_id` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '2', '我一个月不更新了，咋地', '1', null, null, null, '2024-05-21 15:22:24', '2024-05-22 23:35:14', null);
INSERT INTO `comment` VALUES ('2', '1', '1234', '2', null, null, null, '2024-05-21 15:24:54', '2024-05-21 15:24:54', null);
INSERT INTO `comment` VALUES ('3', '2', '好厉害哦', '2', null, null, null, '2024-05-22 00:28:13', '2024-05-22 00:28:13', null);
INSERT INTO `comment` VALUES ('4', '2', '我也觉得', '1', null, '3', null, '2024-05-22 16:09:31', '2024-05-22 16:09:31', null);
INSERT INTO `comment` VALUES ('5', '2', '史莱姆单杀！', '8', null, '3', null, '2024-05-22 16:15:19', '2024-05-22 16:15:19', null);
INSERT INTO `comment` VALUES ('10', '2', '失误失误', '1', null, '3', '5', '2024-05-22 22:51:27', '2024-05-22 23:34:23', null);

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `video_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `post_id` (`video_id`) USING BTREE,
  CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_german2_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of likes
-- ----------------------------
INSERT INTO `likes` VALUES ('20', '1', '1', '2024-05-21 00:55:19');
INSERT INTO `likes` VALUES ('23', '2', '1', '2024-05-21 13:07:51');
INSERT INTO `likes` VALUES ('29', '4', '2', '2024-05-21 13:44:15');
INSERT INTO `likes` VALUES ('30', '8', '2', '2024-05-21 13:44:22');
INSERT INTO `likes` VALUES ('33', '10', '2', '2024-05-21 14:46:25');
INSERT INTO `likes` VALUES ('36', '1', '2', '2024-05-23 15:49:23');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '用户');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_german2_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `birth` date DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `tag` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`account`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '123', '123', '木鱼与摸鱼', '2024-05-09', 'woman', '199***', 'http://8.138.112.13:8180/Y-stand/木鱼与摸鱼.jpg', '百万粉丝up主', '2024-05-15 16:29:40', '2024-05-24 16:35:14');
INSERT INTO `users` VALUES ('2', 'abc', 'abc', 'shuyu', '2024-05-10', 'man', null, 'http://8.138.112.13:8180/Y-stand/de5705a7-0e89-4576-968f-454988f7bfba.png', null, '2024-05-15 16:29:57', '2024-05-24 16:10:53');
INSERT INTO `users` VALUES ('4', '1234', '1234', '黑神话', '2024-05-09', 'man', '123', 'http://8.138.112.13:8180/Y-stand/黑神话悟空.jpg', '黑神话826上线', '2024-05-19 00:32:51', '2024-05-24 16:07:24');
INSERT INTO `users` VALUES ('8', '12344', '1234', '映画君Star', '2024-05-23', 'man', '123', 'http://8.138.112.13:8180/Y-stand/书香哥官方频道.jpg', '电影解说', '2024-05-19 22:15:36', '2024-05-24 16:07:27');
INSERT INTO `users` VALUES ('10', 'Faouzia', 'Faouzia', 'Faouzia', '2000-06-15', 'woman', '123', 'http://8.138.112.13:8180/Y-stand/Faouzia凡希亚.jpg', '大家好！！', '2024-05-20 02:04:08', '2024-05-24 16:15:35');
INSERT INTO `users` VALUES ('13', 'heima', 'heima', '黑马程序员', null, 'man', null, 'http://8.138.112.13:8180/Y-stand/黑马程序员.jpg', '计算机的king', '2024-05-22 15:35:55', '2024-05-24 16:10:54');
INSERT INTO `users` VALUES ('17', '111', '111', '111', null, 'man', null, 'http://8.138.112.13:8180/Y-stand/1d728caf-535c-4a45-8a21-277e4e6eb12a.png', null, '2024-05-22 15:51:02', '2024-05-24 16:10:54');
INSERT INTO `users` VALUES ('18', 'jianbin', 'jianbin', '煎饼果仔呀', null, 'man', null, 'http://8.138.112.13:8180/Y-stand/煎饼果仔呀.jpg', '自媒体有自己的凤凰传奇哈哈哈哈', '2024-05-22 16:47:45', '2024-05-24 16:10:55');
INSERT INTO `users` VALUES ('19', 'xuxuman', 'xuxuman', '咻咻满', null, 'woman', null, 'http://8.138.112.13:8180/Y-stand/咻咻满.jpg', '一名努力的歌手', '2024-05-22 16:48:03', '2024-05-24 16:11:00');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '视频标题',
  `description` text COMMENT '视频描述',
  `url` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频链接',
  `thumbnail` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '视频缩略图链接',
  `view_count` int DEFAULT NULL COMMENT '观看次数',
  `like_count` int DEFAULT NULL COMMENT '点赞次数',
  `coin_count` int DEFAULT NULL COMMENT '硬币数',
  `collect_count` int DEFAULT NULL COMMENT '收藏数量',
  `comment_count` int DEFAULT NULL COMMENT '评论数',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_check` int NOT NULL COMMENT '是否审核',
  PRIMARY KEY (`id`),
  KEY `videro_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('1', '1', '最有操作难度的一集！', '点个关注呗！', 'http://8.138.112.13:8180/Y-stand/1.最有操作难度的一集！(Av1203758405,P1).mp4', 'http://8.138.112.13:8180/Y-stand/最有操作难度的一集！.jpg', null, '1', null, '0', '0', '2024-05-20 01:01:13', '1');
INSERT INTO `video` VALUES ('2', '1', '第N次玩泰拉的我竟成了经验宝宝', '点个关注呗', 'http://8.138.112.13:8180/Y-stand/1.第N次玩泰拉的我竟成了经验宝宝(Av1104124828,P1).mp4', 'http://8.138.112.13:8180/Y-stand/第N次玩泰拉的我竟成了经验宝宝.jpg', '7', '4', '2', '1', '5', '2024-05-21 00:18:57', '1');
INSERT INTO `video` VALUES ('3', '4', '《黑神话：悟空》WeGame预告 | 2024.8.20同步发售', '《黑神话：悟空》全新宣传片已于WeGame游戏之夜发布。\r\n简体中文PC标准版售价 268 元，2024年8月20日WeGame平台同步发售。  \r\n\r\n前往官网：www.heishenhua.com', 'http://8.138.112.13:8180/Y-stand/《黑神话：悟空》WeGame预告 _ 2024.8.20同步发售 - 1.《黑神话：悟空》WeGame预告 _ 2024.8.20同步发售(Av1354896847,P1).mp4', 'http://8.138.112.13:8180/Y-stand/15a23d29bbbff8a79495ffbac61daf0.png', null, null, null, '0', '0', '2024-05-21 23:15:43', '0');
INSERT INTO `video` VALUES ('4', '8', '结局烂尾？千字拆解《新生》十二大暗黑细节“真 全员恶人！”', null, 'http://8.138.112.13:8180/Y-stand/1.结局烂尾？千字拆解《新生》十二大暗黑细节“真 全员恶人！”(Av1304648397,P1).mp4', 'http://8.138.112.13:8180/Y-stand/结局烂尾？千字拆解《新生》十二大暗黑细节“真 全员恶人！”.jpg', null, null, null, '0', '0', '2024-05-21 23:16:12', '1');
INSERT INTO `video` VALUES ('5', '2', '爆笑父子场！轮椅装着霰弹枪能不沉吗，父子俩装病不要太好笑，还有这太医好眼熟哦', null, 'http://8.138.112.13:8180/Y-stand/1.爆笑父子场！轮椅装着霰弹枪能不沉吗，父子俩装病不要太好笑，还有这太医好眼熟哦(Av1554891053,P1).mp4', 'http://8.138.112.13:8180/Y-stand/爆笑父子场！轮椅装着霰弹枪能不沉吗，父子俩装病不要太好笑，还有这太医好眼熟哦.jpg', null, null, null, '0', '0', '2024-05-21 23:16:27', '1');
INSERT INTO `video` VALUES ('6', '10', '【Faouzia凡希亚】亚亚歌手2024初舞台！', 'does that makes you crazy?', 'http://8.138.112.13:8180/Y-stand/1.【Faouzia凡希亚】亚亚歌手2024初舞台！(Av1954280219,P1).mp4', 'http://8.138.112.13:8180/Y-stand/【Faouzia凡希亚】亚亚歌手2024初舞台！.jpg', null, null, null, '0', '0', '2024-05-21 23:16:38', '1');
INSERT INTO `video` VALUES ('7', '13', '黑马程序员Java零基础视频教程_上部(Java入门，含斯坦福大学练习题+力扣算法题和大厂java面试题）', '传智教育·黑马程序员Java研究院全新录制的Java入门教程', 'http://8.138.112.13:8180/Y-stand/1.Java入门-01-Java学习介绍(Av298622138,P1).mp4', 'http://8.138.112.13:8180/Y-stand/黑马程序员Java零基础视频教程_上部(Java入门，含斯坦福大学练习题+力扣算法题和大厂java面试题）.jpg', null, null, null, '0', '0', '2024-05-08 23:47:11', '1');
INSERT INTO `video` VALUES ('8', '8', '2024年最新惊悚单元剧集《9号秘事》第九季第2集：这则故事中谁的选择是对的？', '兄弟们怎么选择？', 'http://8.138.112.13:8180/Y-stand/1.2024年最新惊悚单元剧集《9号秘事》第九季第2集：这则故事中谁的选择是对的(Av1754621253,P1).mp4', 'http://8.138.112.13:8180/Y-stand/2024年最新惊悚单元剧集《9号秘事》第九季第2集：这则故事中谁的选择是对的？.jpg', null, null, null, null, null, '2024-05-22 16:24:10', '1');
INSERT INTO `video` VALUES ('9', '1', '《无伤通关》', '点个关注点个关注点个关注，求求求了！', 'http://8.138.112.13:8180/Y-stand/1.《无伤通关》(Av1301874829,P1).mp4', 'http://8.138.112.13:8180/Y-stand/《无伤通关》.jpg', null, null, null, null, null, '2024-03-16 16:36:45', '1');
INSERT INTO `video` VALUES ('10', '1', '《瞒天但 不过海》', '一个简单的搞笑小剧场', 'http://8.138.112.13:8180/Y-stand/1.《瞒天但 不过海》(Av1151512919,P1).mp4', 'http://8.138.112.13:8180/Y-stand/《瞒天但 不过海》.jpg', null, null, null, null, null, '2024-03-09 16:37:00', '1');
INSERT INTO `video` VALUES ('11', '1', '斗地主日记:谁才是最强菜刀', '点个关注呗，煮播急需关注', 'http://8.138.112.13:8180/Y-stand/斗地主日记_谁才是最强菜刀 - 1.斗地主日记_谁才是最强菜刀(Av1053344163,P1).mp4', 'http://8.138.112.13:8180/Y-stand/43d0566bb693ee4ea9ce4db50911ed7.png', null, null, null, null, null, '2024-04-21 16:37:10', '1');
INSERT INTO `video` VALUES ('12', '18', '互不相识的两个人，在国外拿错对方行李的这件事…', '位是建筑爱好者，一位是自然爱好者，互不相识的两个人，在国外拿错对方行李的这件事…', 'http://8.138.112.13:8180/Y-stand/1.互不相识的两个人，在国外拿错对方行李的这件事…(Av1854707157,P1).mp4', 'http://8.138.112.13:8180/Y-stand/互不相识的两个人，在国外拿错对方行李的这件事….jpg', null, null, null, null, null, '2024-05-22 16:50:32', '1');
INSERT INTO `video` VALUES ('13', '19', '假如学邓紫棋的声音参加“鸽手”，唱《不为谁而作的歌》！', '本视频纯属娱乐，不喜勿喷，唱的不像，多多见谅！', 'http://8.138.112.13:8180/Y-stand/1.假如用邓紫棋的味道参加“鸽手”唱《不为谁而作的歌》！(Av1204792926,P1).mp4', 'http://8.138.112.13:8180/Y-stand/假如学邓紫棋的声音参加“鸽手”，唱《不为谁而作的歌》！.jpg', '1', null, '1', '1', null, '2024-05-22 16:50:57', '1');
INSERT INTO `video` VALUES ('24', '2', '我不允许有人没看过这个现场｜д•´)!!〖Adam Lambert〗Runnin\'/Chokehold/Sleepwalker', '2016维也纳演唱会', 'http://8.138.112.13:8180/Y-stand/我不允许有人没看过这个现场.mp4', 'http://8.138.112.13:8180/Y-stand/Adam Lambert - Runnin_ _Chokehold_Sleepwalker (live) Vienna 2016 Austria.jpg', '2', null, null, null, null, '2024-05-24 14:15:02', '1');

-- ----------------------------
-- Table structure for view
-- ----------------------------
DROP TABLE IF EXISTS `view`;
CREATE TABLE `view` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `video_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `view_video_id` (`video_id`),
  KEY `view_user_id` (`user_id`),
  CONSTRAINT `view_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `view_video_id` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of view
-- ----------------------------
INSERT INTO `view` VALUES ('1', '1', '2', '2024-05-21 14:45:32');
INSERT INTO `view` VALUES ('2', '2', '2', '2024-05-21 14:46:12');
INSERT INTO `view` VALUES ('3', '1', '13', '2024-05-23 16:00:37');
INSERT INTO `view` VALUES ('4', '1', '2', '2024-05-23 16:13:49');
INSERT INTO `view` VALUES ('5', '1', '2', '2024-05-23 16:13:58');
INSERT INTO `view` VALUES ('12', '1', '24', '2024-05-24 14:16:26');
INSERT INTO `view` VALUES ('13', '1', '2', '2024-05-24 14:56:01');
INSERT INTO `view` VALUES ('14', '1', '2', '2024-05-24 15:00:20');
INSERT INTO `view` VALUES ('15', '1', '24', '2024-05-24 15:00:28');
INSERT INTO `view` VALUES ('16', '1', '2', '2024-05-24 15:09:25');
