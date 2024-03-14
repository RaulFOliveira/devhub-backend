create table especialidade(
    id bigint primary key auto_increment,
    fk_freelancer bigint not null,
    descricao varchar(255) not null
);