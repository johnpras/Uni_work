create database ask3;
use ask3;
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

alter table spoudastes add examino varchar(20);
update spoudastes set examino = 'G' where onoma='makis';
update spoudastes set examino = 'G' where onoma='takis';
update spoudastes set examino = 'D' where onoma='soula';
update spoudastes set examino = 'E' where onoma='mitsos';

alter table spoudastes add constraint pk_mitrwo primary key (mitrwo);
alter table mathimata add constraint pk_kwdikos primary key (kwdikos);
alter table vathmoi add constraint fk_mitrwo foreign key (mitrwo) references spoudastes(mitrwo);
alter table vathmoi add constraint fk_kwdikos foreign key(kwdikos) references mathimata(kwdikos);

create view spoudastes_view(s_mitrwo,s_onoma,s_examino) as select mitrwo,onoma,examino from spoudastes;

drop view examino_view;
create view examino_view(v_mitrwo,v_onoma,v_examino) as select mitrwo,onoma,examino from spoudastes where examino = 'G' with check option;

create view examino2_view(v_mitrwo,v_onoma,v_examino) as select mitrwo,onoma,examino from spoudastes where examino in (select examino from spoudastes where examino = 'G');

drop table vathmoi;
drop table spoudastes;
drop table mathimata;
drop database ask3;
