CREATE TABLE mensagem (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    fk_remetente bigint NOT NULL,
    tipo_remetente varchar(11) not null,
    fk_destinatario bigint NOT NULL,
    conteudo varchar(999) NOT NULL,
    send_at datetime NOT NULL,
    fk_conversa bigint NOT NULL,
    FOREIGN KEY (fk_conversa) REFERENCES conversa(id)
);