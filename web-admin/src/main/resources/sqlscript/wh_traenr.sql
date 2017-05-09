
CREATE TABLE wh_traenr (
  enrid varchar(16) NOT NULL COMMENT '报名标识',
  traitmid varchar(16) NOT NULL COMMENT '培训批次标识',
  userid varchar(16) NOT NULL COMMENT '用户标识',
  isa varchar(2) NOT NULL COMMENT '是否已完善资料:0-未完善资料；1-已完善资料；2-不需要',
  isb varchar(2) NOT NULL COMMENT '是否已上传附件:0-未上传附件；1-已上传附件；2-不需要',
  attachments varchar(128) NOT NULL COMMENT '附件地址',
  state varchar(2) NOT NULL COMMENT '审核状态:0-未审核;1-已审核;2-不需要',
  PRIMARY KEY (enrid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE wh_traenr_his (
  enrid varchar(16) NOT NULL COMMENT '报名标识',
  traitmid varchar(16) NOT NULL COMMENT '培训批次标识',
  userid varchar(16) NOT NULL COMMENT '用户标识',
  isa varchar(2) NOT NULL COMMENT '是否已完善资料:0-未完善资料；1-已完善资料；2-不需要',
  isb varchar(2) NOT NULL COMMENT '是否已上传附件:0-未上传附件；1-已上传附件；2-不需要',
  attachments varchar(128) NOT NULL COMMENT '附件地址',
  state varchar(2) NOT NULL COMMENT '审核状态:0-未审核;1-已审核;2-不需要',
  PRIMARY KEY (enrid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;










