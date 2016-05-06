DROP DATABASE IF EXISTS customer;

CREATE DATABASE customer;

USE customer;

DROP USER 'cdavis'@'localhost';
FLUSH PRIVILEGES;
CREATE USER 'cdavis'@'localhost' IDENTIFIED BY 'fall2013';
GRANT ALL PRIVILEGES ON .* TO 'cdavis'@'localhost' IDENTIFIED BY 'fall2013';
FLUSH PRIVILEGES;

CREATE TABLE Customers
(
	AccountNumber	int				NOT NULL	PRIMARY KEY,
	Name 			varchar(20)		NOT NULL,
	Address			varchar(100),
	CustomerType	varchar(10)
);

CREATE TABLE Courses
(
	CourseID		int				NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
	Title			varchar(20)		NOT NULL,
	Instructor		varchar(20),
	Price			double(20,2),
	CourseType		varchar(20),
	StartDate		date,
	EndDate			date,
	Proctor			varchar(20),
	Video			boolean,
	Chapters		int,
	Room			varchar(20),
	StartTime		time,
	EndTime			time
);

CREATE TABLE CustomerCourses
(
	AccountNumber	int				NOT NULL	PRIMARY KEY,
	Name			varchar(20)		NOT NULL,
	Course1			int,
	Course2			int,
	Course3			int,
	Course4			int,
	Course5			int
);

INSERT INTO Customers
VALUES(12345,'Jones','786 Cooper;Arlington;Texas;76019','student');

INSERT INTO Customers
VALUES(65489,'Smith','921 Bowen;Arlington;Texas;76006','faculty');

INSERT INTO Customers
VALUES(54367,'Barker','621 Lancaster;Fort Worth;Texas;76090','student');

INSERT INTO Customers
VALUES(98712,'Callan','978 Lowe;Dallas;Texas;75009','faculty');

INSERT INTO Customers
VALUES(25968,'Willis','123 King;Tulsa;Oklahoma;56909','government');

INSERT INTO Customers
VALUES(73674,'James','109 Baker;Dallas;Texas;75010','faculty');

INSERT INTO Customers
VALUES(26789,'Parsons','4523 Azalea;Burleson;Texas;76134','government');

INSERT INTO Courses
VALUES(0,'Java1','Davis',125.00,'programming','2015-01-01','2015-02-01','UTA',true,12,null,null,null);

INSERT INTO Courses
VALUES(0,'Java2','Davis',125.00,'programming','2015-02-01','2015-03-01','UTA',true,12,null,null,null);

INSERT INTO Courses
VALUES(0,'Relieve Stress','Jones',35.00,'misc','2015-03-02','2015-03-31','none',false,5,null,null,null);

INSERT INTO Courses
VALUES(0,'Painter 2015','Vikram',59.00,'painting','2015-03-02','2015-03-31','TCU',false,8,null,null,null);

INSERT INTO Courses
VALUES(0,'Canon Pictures','Long',75.00,'photography','2015-01-15','2015-02-03',null,null,null,'COB 142','13:30','15:30');

INSERT INTO Courses
VALUES(0,'Play The Piano','Smith',40.00,'music','2015-04-01','2015-05-01',null,null,null,'UH 105','13:30','15:30');

INSERT INTO Courses
VALUES(0,'Acting 101','Simon',69.00,'misc','2015-04-05','2015-06-01',null,null,null,'CH 106','8:00','10:00');

INSERT INTO CustomerCourses
VALUES(12345,'Jones',null,null,null,null,null);

INSERT INTO CustomerCourses
VALUES(65489,'Smith',null,null,null,null,null);

INSERT INTO CustomerCourses
VALUES(54367,'Barker',null,null,null,null,null);

INSERT INTO CustomerCourses
VALUES(98712,'Callan',null,null,null,null,null);

INSERT INTO CustomerCourses
VALUES(25968,'Willis',null,null,null,null,null);

INSERT INTO CustomerCourses
VALUES(73674,'James',null,null,null,null,null);

INSERT INTO CustomerCourses
VALUES(26789,'Parsons',null,null,null,null,null);