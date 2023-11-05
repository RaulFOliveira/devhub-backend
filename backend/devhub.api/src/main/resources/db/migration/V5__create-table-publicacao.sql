create table publicacao(
  id bigint primary key auto_increment,
  titulo varchar(255) not null ,
  descricao varchar(255) not null,
  fk_contratante bigint not null,
  foreign key (fk_contratante) references contratante(id),
  created_at datetime not null
);