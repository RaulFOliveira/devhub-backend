package com.devhub.api.domain.funcao;

public enum Funcao {
    DESENVOLVEDOR_BACKEND("DESENVOLVEDOR_BACKEND"),
    DESENVOLVEDOR_FRONTEND("DESENVOLVEDOR_FRONTEND"),
    DESENVOLVEDOR_FULLSTACK("DESENVOLVEDOR_FULLSTACK"),
    BANCO_DE_DADOS("BANCO_DE_DADOS"),
    PRODUCT_OWNER("PRODUCT_OWNER"),
    QUALITY_ASSURANCE("QUALITY_ASSURANCE"),
    WEB_DESIGNER("WEB_DESIGNER");

    private String funcao;

    Funcao(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }
}
