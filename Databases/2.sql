create database ask2;
show databases;
use ask2;
create table spoudastes(mitrwo int,onoma varchar(40));
create table mathimata(kwdikos varchar(40),titlos varchar(40));
create table vathmoi(mitrwo int,kwdikos varchar(40),vathmos int);

insert into spoudastes values(1000,'Makis');
insert into spoudastes values(1001,'Takis');
insert into spoudastes values(1002,'Soula');
insert into spoudastes values(1003,'Mitsos');
insert into mathimata values('G101','Programmatismos se java');
insert into mathimata values('D435','Baseis Dedomenwn');
insert into mathimata values('ST222','Dikaio PS');
insert into mathimata values('Z876','Mixaniki Logismikou');
insert into vathmoi values(1000,'D435',5);
insert into vathmoi values(1003,'Z876',10);
insert into vathmoi values(1002,'Z876',7);
insert into vathmoi values(1003,'ST222',6);

alter table spoudastes add primary key (mitrwo);
alter table mathimata add primary key(kwdikos);
alter table vathmoi add foreign key (mitrwo) references spoudastes(mitrwo);
alter table vathmoi add foreign key(kwdikos) references mathimata(kwdikos);

show create table vathmoi;
alter table vathmoi drop foreign key vathmoi_ibfk_1;
alter table vathmoi drop key mitrwo;
alter table vathmoi drop foreign key vathmoi_ibfk_2;
alter table vathmoi drop key kwdikos;
alter table spoudastes drop primary key;
alter table mathimata drop primary key;

alter table spoudastes add constraint pk_mitrwo primary key (mitrwo);
alter table mathimata add constraint pk_kwdikos primary key (kwdikos);
alter table vathmoi add constraint fk_mitrwo foreign key (mitrwo) references spoudastes(mitrwo);
alter table vathmoi add constraint fk_kwdikos foreign key(kwdikos) references mathimata(kwdikos);

select * from spoudastes;
select * from mathimata;
select * from vathmoi;

drop table vathmoi;
drop table spoudastes;
drop table mathimata;
drop database ask2;
