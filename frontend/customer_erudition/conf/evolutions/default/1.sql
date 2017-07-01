
# --- !Ups

CREATE TABLE cc.user (
    id SERIAL,
    email varchar(200) UNIQUE NOT NULL,
    name varchar(255) UNIQUE NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(100) NOT NULL,
    reference int,
    PRIMARY KEY (id)
);
INSERT INTO cc.user values (1,'sushil@knoldus.com','sushil singh','Knoldus','SU',1);

# --- !Downs
DROP TABLE cc.user;