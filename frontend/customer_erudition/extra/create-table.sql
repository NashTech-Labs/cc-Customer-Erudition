CREATE DATABASE cc;

CREATE TABLE cc.user (
    id SERIAL,
    email varchar(200) UNIQUE NOT NULL,
    name varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(100) NOT NULL,
    reference int,
    PRIMARY KEY (id)
);

CREATE TABLE cc.bank (
    id SERIAL,
    name varchar(100) UNIQUE NOT NULL,
    branch varchar(100) UNIQUE NOT NULL,
    ifsc varchar(100) NOT NULL,
    PRIMARY KEY (id)
);