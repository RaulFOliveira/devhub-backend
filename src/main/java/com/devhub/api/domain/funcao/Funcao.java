package com.devhub.api.domain.funcao;

public enum Funcao {
    DESENVOLVEDOR_BACKEND("Desenvolvedor Back-end"),
    DESENVOLVEDOR_FRONTEND("Desenvolvedor Front-end"),
    DESENVOLVEDOR_FULLSTACK("Desenvolvedor Fullstack"),
    BANCO_DE_DADOS("Banco de Dados"),
    PRODUCT_OWNER("Product Owner"),
    QUALITY_ASSURANCE("Quality Assurance"),
    WEB_DESIGNER("Web Designer");

    private String funcao;

    Funcao(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
