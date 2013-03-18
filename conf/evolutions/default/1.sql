# Users table
#
# If we declare the 'password' column as 'char(60)', MySQL will allocate 240 bytes for it
# because we are using the 'utf8mb4' charset. We could declare the 'password' column as
# 'char(60) charset ascii collate ascii_bin', but it is not worth the trouble.

# --- !Ups

create table user
	( id                 bigint         not null auto_increment primary key
	, name               varchar(30)    not null
	, legal_name         varchar(70)    not null
	, email              varchar(100)   not null unique
	, password           varchar(60)    not null
	, status             enum('pending', 'active', 'inactive') not null
	, created            timestamp      not null default now()
	, first_login        timestamp      null
	, last_login         timestamp      null
	, password_changed   timestamp      null
	, failed_attempts    tinyint        not null default 0
	, index (name)
	, index (legal_name)
	)
	engine=InnoDB;

# --- !Downs

drop table user;
