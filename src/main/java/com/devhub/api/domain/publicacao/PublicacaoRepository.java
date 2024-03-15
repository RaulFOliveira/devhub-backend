package com.devhub.api.domain.publicacao;

import com.devhub.api.domain.contratante.Contratante;
import com.devhub.api.domain.publicacao.dto.ListaPublicacaoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    @Query("""
SELECT new com.devhub.api.domain.publicacao.dto.ListaPublicacaoDTO(
    p.id,
    c.nome,
    c.imagem,
    p.descricao,
    p.id_usuario,
    p.createdAt,
    p.role
)
FROM Publicacao p 
JOIN Contratante c ON p.id_usuario = c.id 
WHERE c.id = ?1 AND p.role = 'CONTRATANTE'
ORDER BY p.createdAt DESC
""")
    List<ListaPublicacaoDTO> findByContratante(Long id);

    @Query("""
SELECT new com.devhub.api.domain.publicacao.dto.ListaPublicacaoDTO(
    p.id,
    f.nome,
    f.imagem,
    p.descricao,
    p.id_usuario,
    p.createdAt,
    p.role
)
FROM Publicacao p 
JOIN Freelancer f ON p.id_usuario = f.id 
WHERE f.id = ?1 AND p.role = 'FREELANCER'
ORDER BY p.createdAt DESC
""")
    List<ListaPublicacaoDTO> findByFreelancer(Long id);
    List<Publicacao> findAllByOrderByCreatedAtDesc();

    @Query("""
SELECT new com.devhub.api.domain.publicacao.dto.ListaPublicacaoDTO(
    p.id,
    CASE 
        WHEN p.role = 'CONTRATANTE' THEN c.nome
        WHEN p.role = 'FREELANCER' THEN f.nome
        ELSE NULL 
    END,
    CASE 
        WHEN p.role = 'CONTRATANTE' THEN c.imagem
        WHEN p.role = 'FREELANCER' THEN f.imagem
        ELSE NULL 
    END,
    p.descricao,
    p.id_usuario,
    p.createdAt,
    p.role
)
FROM Publicacao p 
LEFT JOIN Contratante c ON p.id_usuario = c.id AND p.role = 'CONTRATANTE'
LEFT JOIN Freelancer f ON p.id_usuario = f.id AND p.role = 'FREELANCER'
WHERE p.role IN ('CONTRATANTE', 'FREELANCER')
ORDER BY p.createdAt DESC
""")
    List<ListaPublicacaoDTO> findAllPublicacoes();

}
