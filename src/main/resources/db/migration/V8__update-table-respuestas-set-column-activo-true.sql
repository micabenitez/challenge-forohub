alter table respuestas add column activo boolean;
update respuestas set activo = true;