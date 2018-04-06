-- 初始化数据库脚本
-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建表
create table seckill(
 'seckill_id' bigint not null auto_increment comment '',
 'name' varchar(120) not null comment '库存名字',
 'number' int not null comment '库存数量',
 'start_time' TIMESTAMP  not null comment '秒杀开始时间',
 'end_time' TIMESTAMP  not null comment '秒杀结束时间',
 'create_time' TIMESTAMP not null comment '创建时间',
 PRIMARY key (seckill_id),
 key idx_start_time(start_time),
 key idx_end_time(end_time),
 key idx_reate_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 comment '秒杀库存表';

--初始化一些数据
INSERT into
  seckill (name,number,start_time,end_time)
VALUES
  ('1000元秒杀',100,'2005-11-21 12:00:00',2005-11-21 12:00:00);

-- 秒杀成功明细表
-- 用户登录认证相关的信息
create table success_killd(
  'seckill_id' bigint not null comment '秒杀商品id',
  'user_phone' bigint not null comment '用户手机号',
  'state' tinyint not null default -1 comment '状态: -1表示无效 '
  'create_time' TIMESTAMP not null comment '创建时间',
  PRIMARY key(seckill_id,user_phone),/*联合主键 同一用户同一产品只能秒杀一次*/
  KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=UTF8 comment '秒杀成功明细表';

--记录每次上线ddl的记录

