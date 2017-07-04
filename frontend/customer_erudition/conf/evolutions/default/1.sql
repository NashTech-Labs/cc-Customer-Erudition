# --- !Ups
CREATE TABLE IF NOT EXISTS cc.user (
    id SERIAL,
    email varchar(200) UNIQUE NOT NULL,
    name varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(100) NOT NULL,
    reference int,
    PRIMARY KEY (id)
);
INSERT INTO cc.user values (1,'sushil@knoldus.com','sushil singh','Knoldus','SU',1);
INSERT INTO cc.user values (2,'pallavi@knoldus.com','pallavi singh','Knoldus','SU',2);
insert into cc.user values(3,'pallavi@knoldus.in','pallavi','Knoldus','BA',3);
insert into cc.user values(8,'test@gmail.in','test','123456789','AG',8);

# --- !Downs
DROP TABLE cc.user;