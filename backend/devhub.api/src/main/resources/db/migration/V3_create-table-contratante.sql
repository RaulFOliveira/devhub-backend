create table contratante (
    id bigint primary key auto_increment,
    cnpj varchar(14) not null unique,
    fk_usuario bigint,
    foreign key(fk_usuario) references usuario(id)
);