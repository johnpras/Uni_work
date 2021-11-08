create database ask4;
use ask4;
create table spoudastes(mitrwo int,onoma varchar(40), PRIMARY KEY(mitrwo));
create table mathimata(kwdikos varchar(40),titlos varchar(40), PRIMARY KEY(kwdikos));
create table vathmoi(mitrwo int,kwdikos varchar(40),vathmos int, FOREIGN KEY(mitrwo) REFERENCES spoudastes(mitrwo), FOREIGN KEY(kwdikos) REFERENCES mathimata(kwdikos));
insert into spoudastes values(1000,'MAKIS'); 
insert into spoudastes values(1001,'TAKIS'); 
insert into spoudastes values(1002,'SOULA'); 
insert into spoudastes values(1003,'MITSOS'); 
insert into mathimata values('G101','PROGRAMMATISMOS SE JAVA'); 
insert into mathimata values('D435','BASEIS DEDOMENWN'); 
insert into mathimata values('ST222','DIKAIO PS'); 
insert into mathimata values('Z876','MIXANIKI LOGISMIKOU'); 
insert into vathmoi values(1000,'D435',5); 
insert into vathmoi values(1003,'Z876',10); 
insert into vathmoi values(1002,'Z876',7); 
insert into vathmoi values(1003,'ST222',6); 

alter table spoudastes add arithmos_mathimatwn int;
update spoudastes set arithmos_mathimatwn=(select count(*) from vathmoi where spoudastes.mitrwo=vathmoi.mitrwo);

delimiter //
create trigger nameINS
before insert on spoudastes
for each row
begin
set new.onoma = upper(new.onoma);
end;
//
delimiter ;


delimiter //
create trigger nameUPD
before update on spoudastes
for each row
begin
set new.onoma = upper(new.onoma);
end;
//
delimiter ;


delimiter //
create trigger gradeINS
after insert on vathmoi
for each row
begin
update spoudastes 
set arithmos_mathimatwn=arithmos_mathimatwn + 1 
where mitrwo=new.mitrwo and (new.vathmos>=5);
end;
//
delimiter ;


delimiter //
create trigger gradeDEL
after delete on vathmoi
for each row
begin
update spoudastes
set arithmos_mathimatwn=arithmos_mathimatwn - 1 
where mitrwo=old.mitrwo and (old.vathmos>=5);
end;
//
delimiter ;


delimiter //
create trigger gradeUPD
after update on vathmoi
for each row
begin
update spoudastes
set arithmos_mathimatwn=arithmos_mathimatwn + 1 
where mitrwo=new.mitrwo and (new.vathmos>=5);
update spoudastes
set arithmos_mathimatwn=arithmos_mathimatwn - 1 
where mitrwo=old.mitrwo and (old.vathmos>=5);
end;
//
delimiter ;

insert into spoudastes values(9999,'nikolas',2);
update spoudastes set onoma='Makis' where mitrwo=1000;

insert into vathmoi values(1000,'AR22',1);
insert into vathmoi values(1000,'GT22',6);

delete from vathmoi where mitrwo=1000 and kwdikos='GT22';
delete from vathmoi where mitrwo=1000 and kwdikos='AR22';

update vathmoi set vathmos=1 where mitrwo=1002;
update vathmoi set vathmos=10 where mitrwo=1002;

drop database ask4;
