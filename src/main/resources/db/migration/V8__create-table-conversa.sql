create table conversa (
    id bigint primary key auto_increment,
    fk_freelancer bigint not null,
    fk_contratante bigint not null,
    foreign key (fk_contratante) references contratante(id),
    foreign key (fk_freelancer) references freelancer(id)
);