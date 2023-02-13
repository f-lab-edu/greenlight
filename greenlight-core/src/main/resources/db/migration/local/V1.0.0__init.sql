DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS member;

CREATE TABLE USERS (
   user_id varchar(40) not null,
   username varchar(80) not null,
   password varchar(255) not null,
   nickname varchar(80),
   activated tinyint(1) NOT NULL DEFAULT '1',
   PRIMARY KEY (user_id)
) ENGINE=InnoDB charset = utf8mb4;

CREATE TABLE authority (
   authority_name varchar(40) not null,
   PRIMARY KEY (authority_name)
) ENGINE=InnoDB charset = utf8mb4;

CREATE TABLE user_authority (
    user_id varchar(40) not null,
    authority_name varchar(40) not null
) ENGINE=InnoDB charset = utf8mb4;

CREATE TABLE member (
    id int auto_increment primary key,
    email      varchar(45) null,
    password   varchar(45) null,
    nickname   varchar(45) null,
    phone varchar(45) null,
    role       varchar(45) null
) ENGINE=InnoDB charset = utf8mb4;
