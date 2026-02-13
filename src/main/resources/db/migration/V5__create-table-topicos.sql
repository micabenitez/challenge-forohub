CREATE TABLE topicos (
    id bigint not null auto_increment,
    titulo varchar(255) not null,
    mensaje text not null,
    fecha_creacion datetime not null,
    status varchar(100),
    usuario_id bigint not null ,
    curso_id bigint not null,

    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topicos_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);