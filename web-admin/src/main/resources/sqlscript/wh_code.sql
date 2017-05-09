CREATE TABLE `wh_code` (
  `id` varchar(30) NOT NULL,
  `msgPhone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `msgTime` datetime DEFAULT NULL COMMENT '发送时间',
  `msgContent` varchar(50) DEFAULT NULL COMMENT '手机验证码',
  `emailCode` varchar(50) DEFAULT NULL COMMENT '邮箱验证码',
  `emailState` int(2) DEFAULT NULL COMMENT '邮箱状态  0：未激活 1：已激活',
  `emailAddr` varchar(150) DEFAULT NULL COMMENT '邮箱地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

