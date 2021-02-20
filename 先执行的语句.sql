/*create table ?(date char(10),weather varchar(10),emotion varchar(20),title varchar(50),content varchar(5000));
每个用户一张日记表，每注册一个用户自动创建一张表名和用户名相同的表用于存该用户的日记
以下语句初始化了一个用户firstuser a?123456
*/
create database diarymenu character set utf8 collate utf8_bin;
use diarymenu;
create table users(username varchar(20) primary key,displayname varchar(20),pwd varchar(30),mail varchar(30),pwdprotectq int,pwdprotecta varchar(50),setting varchar(10) default 'summer');
insert into users (username,displayname,pwd,mail,pwdprotectq,pwdprotecta) values('firstuser','user1','a?123456','123456@qq.com',4,'blue');
create table firstuser(date char(10),weather varchar(10),emotion varchar(20),title varchar(50),content varchar(5000));
insert into firstuser values('2019-12-04','Sunny','Cheerful','java','Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，
因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程 。
Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等');
insert into firstuser values('2019-12-05','Windy','Peaceful','test2','this is the second test content.');
insert into firstuser values('2019-12-06','Snowy','Peaceful','test3','this is the third test content.');
insert into firstuser values('2019-12-07','Rainy','Sad','test4','this is the first fourth content.');
insert into firstuser values('2019-12-10','Sunny','Others','test5','this is the fifth test content.');
insert into firstuser values('2019-12-11','Sunny','Others','test6','this is the sixth test content.');
insert into firstuser values('2019-12-12','Windy','Sad','test7','this is the seventh test content.');
insert into firstuser values('2019-12-14','Windy','Others','test8','this is the eighth test content.');