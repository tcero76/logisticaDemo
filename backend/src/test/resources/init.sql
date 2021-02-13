DROP ALL OBJECTS;

CREATE TABLE material (
  idmaterial int(11) NOT NULL,
  nombre varchar(255) DEFAULT NULL,
  precio double DEFAULT NULL
);

INSERT INTO material(idmaterial,nombre,precio) VALUES(1,'tijera',5066);

CREATE TABLE usuario (
  idusuario int(11) NOT NULL,
  nombre varchar(255) DEFAULT NULL,
  idrol int(11) DEFAULT NULL,
  clave varchar(75) DEFAULT NULL,
  activo tinyint(1) DEFAULT NULL,
  idalmacen int(11) DEFAULT NULL
 );

INSERT INTO usuario VALUES(1,'llastra',1,'$2a$10$Lqk.R6ZhEpxB9fiFpFYQzeyXmJ5Vi7FIuMlZsjrgTEvGnxv96fXJS',1,1);

CREATE TABLE rol (
  idrol int(11) NOT NULL,
  nombre varchar(255) DEFAULT NULL
);

INSERT INTO rol VALUES (1,'ROLE_ADMIN');

CREATE TABLE almacen (
  idalmacen int(11) NOT NULL,
  fecharegistro datetime DEFAULT NULL,
  nombre varchar(255) DEFAULT NULL,
  idusuario int(11) DEFAULT NULL
);

INSERT INTO almacen VALUES (1,'2019-12-05 22:45:47','Santiago',1);

CREATE TABLE zona (
  idzona int(11) NOT NULL,
  fecharegistro datetime DEFAULT NULL,
  nombre varchar(255) DEFAULT NULL,
  idalmacen int(11) DEFAULT NULL,
  idusuario int(11) DEFAULT NULL
);

INSERT INTO zona VALUES (1,'2020-03-17 21:57:29','X',1,1);
INSERT INTO zona VALUES (2,'2020-03-17 21:57:29','Y',1,1);

CREATE TABLE nivel (
  idnivel int(11) NOT NULL,
  fecharegistro datetime DEFAULT NULL,
  nombre varchar(255) DEFAULT NULL,
  idusuario int(11) DEFAULT NULL,
  idzona int(11) DEFAULT NULL
);

INSERT INTO nivel VALUES (1,NULL,'1',NULL,2);

CREATE TABLE pos (
  idpos int(11) NOT NULL,
  fecharegistro datetime DEFAULT NULL,
  nombre varchar(255) DEFAULT NULL,
  idnivel int(11) DEFAULT NULL,
  idusuario int(11) DEFAULT NULL
  );

INSERT INTO pos VALUES (1,NULL,'A',1,NULL);

CREATE TABLE inventario (
  idinventario int(11) NOT NULL,
  cantidad double DEFAULT NULL,
  cantidadtotal double DEFAULT NULL,
  fecharegistro datetime DEFAULT NULL,
  idmaterial int(11) DEFAULT NULL,
  idpos int(11) DEFAULT NULL,
  idusuario int(11) DEFAULT NULL,
  idoritem int(11) DEFAULT NULL,
  idoditem int(11) DEFAULT NULL
  );

INSERT INTO inventario VALUES (33,65,65,'2020-01-13 04:37:05',1,1,1,34,NULL);