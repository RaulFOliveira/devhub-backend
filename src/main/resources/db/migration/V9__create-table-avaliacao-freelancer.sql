CREATE TABLE avaliacao_freelancer (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    fk_avaliador bigint NOT NULL,
    fk_avaliado bigint NOT NULL,
    nota double NOT NULL,
    foreign key (fk_avaliador) references contratante(id),
    foreign key (fk_avaliado) references freelancer(id)
);
