package com.devhub.api.domain.servico;

public record FinishServicoDTO(Long idContratante, Long idFreelancer, Double valorHora, String nomeRemetente, String destinatario, String nomeDestinatario) {

}
