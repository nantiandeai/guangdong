CREATE TABLE `wh_mgr` (
  `id` varchar(30) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `email` varchar(150) DEFAULT NULL COMMENT '电子邮箱',
  `status` int(11) DEFAULT NULL COMMENT '状态 0：禁用 1：启用',
  `lastdate` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

