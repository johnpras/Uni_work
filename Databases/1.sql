create database ask1;
show databases;
use ask1;

create table spoudastes(mitrwo int,onoma varchar(40));
insert into spoudastes values(1000,'Makis');
insert into spoudastes values(1001,'Takis');
insert into spoudastes values(1002,'Soula');
insert into spoudastes values(1003,'Mitsos');
select * from spoudastes;

create table mathimata(kwdikos varchar(40),titlos varchar(40));
insert into mathimata values('G101','Programmatismos se java');
insert into mathimata values('D435','Baseis Dedomenwn');
insert into mathimata values('ST222','Dikaio PS');
insert into mathimata values('Z876','Mixaniki Logismikou');
select * from mathimata;

create table vathmoi(mitrwo int,kwdikos varchar(40),vathmos int);
insert into vathmoi values(1000,'D435',5);
insert into vathmoi values(1003,'Z876',10);
insert into vathmoi values(1002,'Z876',7);
insert into vathmoi values(1003,'ST222',6);
select * from vathmoi;


select onoma,mitrwo from spoudastes;

select spoudastes.onoma, spoudastes.mitrwo
     from spoudastes
     inner join vathmoi on spoudastes.mitrwo=vathmoi.mitrwo where vathmos<10;

select titlos from mathimata
     inner join vathmoi on vathmoi.kwdikos=mathimata.kwdikos
     inner join spoudastes on spoudastes.mitrwo=vathmoi.mitrwo
     where vathmos>=5 and onoma='Mitsos';
