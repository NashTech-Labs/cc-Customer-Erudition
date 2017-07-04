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

# --- Super user

CREATE TABLE IF NOT EXISTS cc.bank (
    id SERIAL,
    name varchar(200),
    branch varchar(200),
    ifsc_code varchar(200),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cc.agent(
    id SERIAL,
    name varchar(200),
    created_by int,
    PRIMARY KEY (id)
);

INSERT INTO cc.user values (1,'sushil@knoldus.com','sushil singh','Knoldus','SU',null);
INSERT INTO cc.user values (2,'pallavi@knoldus.com','pallavi singh','Knoldus','SU',null);

insert into cc.user values(3,'pallavi@knoldus.in','pallavi','Knoldus','BA',3);
insert into cc.user values(4,'pallavi2@knoldus.in','pallavi','Knoldus','BA',2);

insert into cc.user values(8,'test@gmail.in','test','123456789','AG',3);
insert into cc.user values(9,'test2@gmail.in','test','123456789','AG',4);

insert into cc.bank values(3, 'PallaviBank', 'Noida, India', 'IN000111010101');

insert into cc.agent values(8, 'TestAgent', 3);
insert into cc.agent values(9, 'TestAgent2', 4);


# --- !Downs
DROP TABLE cc.user;