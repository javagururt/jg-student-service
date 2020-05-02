CREATE SCHEMA IF NOT EXISTS studentservice DEFAULT CHARACTER SET utf8 ;
USE studentservice;

create table students(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40),
    PRIMARY KEY (id)
);
