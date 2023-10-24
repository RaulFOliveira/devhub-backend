create table servico (
id bigint primary key auto_increment,
fk_contratante bigint not null,
fk_freelancer bigint not null,
duracao bigint not null,
horas_trabalhadas varchar(45) not null,
created_at datetime not null,
foreign key (fk_contratante) references contratante(id),
foreign key (fk_freelancer) references freelancer(id)
);