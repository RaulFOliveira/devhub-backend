create table servico (
id bigint primary key auto_increment,
fk_contratante bigint not null,
fk_freelancer bigint not null,
status varchar(45) not null,
valor_pagamento decimal(7,2),
created_at datetime not null,
finished_at datetime,
foreign key (fk_contratante) references contratante(id),
foreign key (fk_freelancer) references freelancer(id)
);