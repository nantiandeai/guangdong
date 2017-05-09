CREATE TABLE `wh_user` (
  `id` varchar(30) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(150) DEFAULT NULL COMMENT '电子邮件',
  `lastdate` datetime DEFAULT NULL COMMENT '最后登录时间',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别 0：女  1：男',
  `job` varchar(150) DEFAULT NULL COMMENT '职业',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq账号',
  `wx` varchar(100) DEFAULT NULL COMMENT '微信账号',
  `password` varchar(150) DEFAULT NULL COMMENT '注册密码',
  `nation` varchar(100) DEFAULT NULL COMMENT '民族',
  `origo` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `company` varchar(150) DEFAULT NULL COMMENT '工作单位',
  `address` varchar(200) DEFAULT NULL COMMENT '通讯地址',
  `resume` varchar(500) DEFAULT NULL COMMENT '个人简历',
  `actBrief` varchar(500) DEFAULT NULL COMMENT '从事文艺活动简介',
  `isrealname` int(11) DEFAULT NULL COMMENT '是否实名 0:否 1：是',
  `isperfect` int(11) DEFAULT NULL COMMENT '是否完善资料 0：否 1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

