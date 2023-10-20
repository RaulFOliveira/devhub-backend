create table freelancer (
    id_freelancer bigint primary key auto_increment,
    cpf varchar(14) not null unique,
    funcao varchar(100) not null,
    valor_hora decimal(7,2) not null,
    descricao text not null,
    fk_usuario bigint,
    foreign key(fk_usuario) references usuario(id)
);