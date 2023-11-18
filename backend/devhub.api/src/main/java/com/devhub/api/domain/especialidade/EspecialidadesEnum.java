package com.devhub.api.domain.especialidade;

public enum EspecialidadesEnum {
    JAVA("Java"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    C_SHARP("C#"),
    RUBY("Ruby"),
    PHP("PHP"),
    SWIFT("Swift"),
    KOTLIN("Kotlin"),
    TYPESCRIPT("TypeScript"),
    GO("Go"),
    DART("Dart"),
    RUST("Rust"),
    OBJECTIVE_C("Objective-C"),
    SQL("SQL"),
    HTML_CSS("HTML/CSS"),
    SHELL_SCRIPTING("Shell Scripting"),
    MATLAB("MATLAB"),
    R("R"),
    SCALA("Scala"),
    GROOVY("Groovy"),
    LUA("Lua"),
    PERL("Perl"),
    HASKELL("Haskell"),
    C_PLUS_PLUS("C++"),
    C("C"),
    DOT_NET(".NET"),
    NODE_JS("Node.js"),
    ANGULAR("Angular"),
    REACT("React"),
    VUE_JS("Vue.js"),
    EXPRESS_JS("Express.js"),
    SPRING("Spring"),
    DJANGO("Django"),
    RUBY_ON_RAILS("Ruby on Rails"),
    ANDROID("Android"),
    IOS("iOS"),
    AWS("AWS"),
    AZURE("Azure"),
    GOOGLE_CLOUD("Google Cloud"),
    DEVOPS("DevOps"),
    CI_CD("CI/CD"),
    CONTAINERS("Containers"),
    VIRTUALIZATION("Virtualization"),
    BANCO_DE_DADOS("Banco de Dados"),
    ARQUITETURA_DE_SOFTWARE("Arquitetura de Software"),
    DESIGN_PATTERNS("Design Patterns"),
    TESTES_AUTOMATIZADOS("Testes Automatizados"),
    CONTROLE_DE_VERSAO("Controle de Versão"),
    METODOLOGIAS_AGILE("Metodologias Ágeis"),
    DESENVOLVIMENTO_ORIENTADO_A_OBJETOS("Desenvolvimento Orientado a Objetos");

    private final String especialidade;

    EspecialidadesEnum(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
