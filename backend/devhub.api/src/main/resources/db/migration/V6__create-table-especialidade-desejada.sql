create table especialidade_desejada(
  id bigint primary key auto_increment,
  nome_especialidade varchar(100) not null,
  fk_publicacao bigint not null,
  foreign key (fk_publicacao) references publicacao(id)
);