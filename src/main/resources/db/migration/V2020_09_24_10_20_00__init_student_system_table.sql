CREATE TABLE IF NOT EXISTS `trainer` (
  id int(11) PRIMARY KEY AUTO_INCREMENT,
  name varchar(255)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `trainee` (
  `id`     int(11)      PRIMARY KEY AUTO_INCREMENT,
  `name`   varchar(255),
  `office` varchar(255),
  `email`  varchar(255),
  `github` varchar(255),
  `zoomId` varchar(255)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


--INSERT INTO `trainer` VALUES (1, '桂溪京');
--INSERT INTO `trainer` VALUES (2, '张钊');
--INSERT INTO `trainer` VALUES (3, '朱玉前');
--INSERT INTO `trainer` VALUES (4, '彭梦秋');
--INSERT INTO `trainer` VALUES (5, '董志刚');
--INSERT INTO `trainer` VALUES (6, '张巍');
--INSERT INTO `trainer` VALUES (7, '杜鹃');
--INSERT INTO `trainer` VALUES (8, '王晓峰');
--INSERT INTO `trainer` VALUES (9, '王雅君');
--
--INSERT INTO `trainee` VALUES (1, '沈乐棋', '武汉', '8801@thoughtworks.com', 'https://github.com/shenyueqi', '22221');
--INSERT INTO `trainee` VALUES (2, '徐慧慧', '武汉', '8802@thoughtworks.com', 'https://github.com/shenyueqi', '22222');
--INSERT INTO `trainee` VALUES (3, '陈思聪', '武汉', '8803@thoughtworks.com', 'https://github.com/shenyueqi', '22223');
--INSERT INTO `trainee` VALUES (4, '王江林', '武汉', '8804@thoughtworks.com', 'https://github.com/shenyueqi', '22224');
--INSERT INTO `trainee` VALUES (5, '王登宇', '武汉', '8805@thoughtworks.com', 'https://github.com/shenyueqi', '22225');
--INSERT INTO `trainee` VALUES (6, '杨思雨', '武汉', '8806@thoughtworks.com', 'https://github.com/shenyueqi', '22226');
--INSERT INTO `trainee` VALUES (7, '江雨舟', '武汉', '8807@thoughtworks.com', 'https://github.com/shenyueqi', '22227');
--INSERT INTO `trainee` VALUES (8, '廖燊', '武汉', '8808@thoughtworks.com', 'https://github.com/shenyueqi', '22228');
--INSERT INTO `trainee` VALUES (9, '胡晓', '武汉', '8809@thoughtworks.com', 'https://github.com/shenyueqi', '22229');
--INSERT INTO `trainee` VALUES (10, '但杰', '武汉', '8810@thoughtworks.com', 'https://github.com/shenyueqi', '222210');