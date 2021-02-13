create database if not exists qop charset = utf8mb4;
use qop;
drop table if exists qop_user;
drop table if exists qop_group;
drop table if exists qop_group_member;
drop table if exists qop_group_questionnaire;
create table qop_user
(
    id             bigint auto_increment primary key,
    nick_name      varchar(20) not null comment '昵称',
    phone_number   varchar(50) unique comment '手机号',
    email          varchar(50) unique comment '电子邮箱',
    password       varchar(50) not null comment '账号密码',
    img            varchar(100),
    account_status varchar(5) default '00' comment '账号状态',
    create_date    datetime   default now() comment '创建时间'
);
create table qop_group
(
    id           bigint auto_increment primary key,
    name         varchar(50) not null comment '群组名',
    introduction varchar(200) comment '群组介绍',
    img          varchar(100),
    create_date  datetime default now() comment '创建时间'
);
create table qop_group_member
(
    id        bigint auto_increment primary key,
    group_id  bigint not null comment '群组id',
    user_id   bigint not null comment '用户id',
    user_role varchar(5) default '00' comment '用户角色',
    join_date datetime   default now() comment '加入时间'
);
create table qop_group_questionnaire
(
    id               bigint auto_increment primary key,
    group_id         bigint      not null comment '群组id',
    questionnaire_id varchar(20) not null comment '问卷id',
    publish_date     datetime default now() comment '发布时间'
);