use codefarm;
drop table if exists `codefarm`.user;

CREATE TABLE `codefarm`.`user` (
  `id` BIGINT(100) NOT NULL COMMENT '主键',
  `username` VARCHAR(45) NULL COMMENT '用户名',
  `birthday` DATE NULL,
  `created` DATETIME NULL COMMENT '创建时间',
  PRIMARY KEY (`id`))
COMMENT = '用户表';
