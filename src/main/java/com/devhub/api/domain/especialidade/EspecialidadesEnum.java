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
    SHELL_SCRIPT("Shell Script"),
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
    SPRINGBOOT("SpringBoot"),
    DJANGO("Django"),
    RUBY_ON_RAILS("Ruby on Rails"),
    ANDROID("Android"),
    IOS("iOS"),
    AWS("AWS"),
    AZURE("Azure"),
    GOOGLE_CLOUD("Google Cloud"),
    CI_CD("CI/CD"),
    BANCO_DE_DADOS("Banco de Dados"),
    ARQUITETURA_DE_SOFTWARE("Arquitetura de Software"),
    DESIGN_PATTERNS("Design Patterns"),
    METODOLOGIAS_AGILE("Metodologias Ágeis"),
    AZURE_DEVOPS("Azure DevOps"),
    JIRA("Jira"),
    TRELLO("Trello"),
    PLANNER("Planner");

    private final String especialidade;

    EspecialidadesEnum(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
