create table freelancer (
    id bigint primary key auto_increment,
    nome varchar(75) not null,
    telefone varchar(11) unique not null,
    contratacoes int not null default 0,
    email varchar(45) not null,
    senha varchar(255) not null,
    role varchar(11) not null,
    ativo tinyint not null,
    cpf varchar(14) not null unique,
    funcao varchar(100) not null,
    valor_hora decimal(7,2) not null,
    descricao text not null,
    senioridade varchar(6) not null,
    imagem longblob not null
);