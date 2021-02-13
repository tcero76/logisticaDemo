CREATE TABLE cuentaitem (
  idcuentaitem int(11) NOT NULL auto_increment,
  idmaterial int(11) DEFAULT NULL,
  cantidad double DEFAULT NULL,
  idpos int(11) DEFAULT NULL,
  idusuario int(11) DEFAULT NULL,
  idcuenta int(11) DEFAULT NULL,
  status varchar(45) DEFAULT NULL
  );

INSERT INTO cuentaitem VALUES (49,2,12,1,1,1,'REALIZADO');

CREATE TABLE cuenta (
  idcuenta int(11) NOT NULL auto_increment,
  idzona int(11) DEFAULT NULL,
  fecharegistro timestamp NOT NULL,
  idusuario int(11) DEFAULT NULL,
  status varchar(25) DEFAULT NULL
  );

INSERT INTO cuenta VALUES (1,2,'2020-04-23 00:40:47',1,'PENDIENTE');

