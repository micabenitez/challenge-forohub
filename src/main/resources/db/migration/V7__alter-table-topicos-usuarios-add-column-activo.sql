alter table topicos add activo boolean;
update topicos set activo = true;

alter table usuarios add activo BOOLEAN;
update usuarios set activo = true;