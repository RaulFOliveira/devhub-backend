create table contratante (
    id bigint primary key auto_increment,
    nome varchar(75) not null,
    telefone varchar(11) unique not null,
    contratacoes int not null default 0,
    email varchar(45) not null,
    senha varchar(255) not null,
    role varchar(11) not null,
    ativo tinyint not null,
    cnpj varchar(14) not null unique
);