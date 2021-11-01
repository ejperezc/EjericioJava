# EjericioJava
EjercicioJava

Se adjunta ejercicio

consulta usuarios: 
GET: http://localhost:8080/api/usuarios
consulta usuario
GET: http://localhost:8080/api/usuario/{id}
crear usuario:
POST: http://localhost:8080/api/usuarios
update usuarios
PUT: http://localhost:8080/api/usuarios/{id}
delete usuarios
DELETE: http://localhost:8080/api/usuarios/{id}

las tablas e insert de script se crean automaticamente en el proyecto, de igual manera adjunto script

insert into usuarios (name,email,password,create,modified,last_login,token,isactive) values ('Esteban','esteban@gmail.com','pass123',sysdate-5,sysdate-4,sysdate-3,'qweh123','0');
insert into usuarios (name,email,password,create,modified,last_login,token,isactive) values ('Jose','je@gmail.com','pass123',sysdate-5,sysdate-4,sysdate-3,'qweh123','0');
insert into usuarios (name,email,password,create,modified,last_login,token,isactive) values ('Judith','marin@gmail.com','pass123',sysdate-5,sysdate-4,sysdate-3,'qweh123','1');

