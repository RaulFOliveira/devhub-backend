create table usuario(
    id bigint primary key auto_increment,
    nome varchar(75) not null,
    telefone varchar(11) unique not null,
    email varchar(100) unique not null,
    senha varchar(255) not null,
    contratacoes int not null default 0,
    role varchar(11) not null,
    ativo tinyint not null
)