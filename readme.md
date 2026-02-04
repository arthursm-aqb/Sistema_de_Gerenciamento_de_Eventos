classDiagram
    %% Relações de Herança (Generalização)
    Pessoa <|-- Participante
    Pessoa <|-- Palestrante

    %% Relações de Implementação (Realização)
    Certificado <|.. Participante
    Certificado <|.. Palestrante

    %% Relações de Associação e Composição
    painel *-- "0..*" evento : gerencia >
    evento o-- "0..*" Participante : contém >
    evento o-- "0..*" Palestrante : contém >
    
    %% Dependências (Uso)
    painel ..> Resumo : retorna >
    Interface ..> painel : usa >
    Interface ..> Resumo : usa >

    %% Definição das Classes

    class Resumo {
        <<Record>>
        +int codigo
        +String mensagem
    }

    class Certificado {
        <<Interface>>
        +getCertificado(String certificado) void
    }

    class Pessoa {
        <<Abstract>>
        #String nome
        #String cpf
        +Pessoa(String nome, String cpf)
        +getNome() String
        +getCpf() String
    }

    class Participante {
        -String certificado
        +Participante(String nome, String cpf)
        +getCertificado(String certificado) void
        +toString() String
    }

    class Palestrante {
        -String especialidade
        -String certificado
        +Palestrante(String nome, String cpf, String especialidade)
        +getEspecialidade() String
        +getCertificado(String certificado) void
        +toString() String
    }

    class evento {
        -String nomeEvento
        -HashSet~Participante~ participantes
        -HashSet~Palestrante~ palestrantes
        -int numPalestrantes
        -int numParticipantes
        -int capacidade
        +evento(String evento, int capacidade)
        +getEvento() String
        +getNomeParticipantes() HashSet~Participante~
        +getNomePalestrantes() HashSet~Palestrante~
        +adicionarParticipantes(Participante unico) int
        +adicionarParticipantes(HashSet~Participante~ nomes) ArrayList~Integer~
        +removerParticipantes(HashSet~Participante~ lista) boolean
        +adicionarPalestrantes(HashSet~Palestrante~ lista) ArrayList~Integer~
        +removerPalestrante(Palestrante p) boolean
        +gerarCertificado(Participante p) void
        +gerarCertificado(Palestrante p) void
    }

    class painel {
        -HashMap~Integer, HashMap~Integer, Integer~~ lotesIngressos
        -int ID
        -HashMap~Integer, evento~ Eventos
        +painel()
        +pegarID(String nomeEvento) int
        +criarEvento(String nome, int capacidade) void
        +criarLotes(String nome, ArrayList~Integer~ lotes) Resumo
        +venderIngresso(String nome, int qtd, Participante p) Resumo
        +venderIngresso(String nome, int qtd, HashSet~Participante~ p) Resumo
        +adicionarPalestrante(String nome, Palestrante p) Resumo
        +adicionarPalestrantes(String nome, HashSet~Palestrante~ p) Resumo
        +removerPalestrante(String nome, String cpf, int id) Resumo
        +buscarParticipante(String nomeEvento, String cpf) Participante
        +buscarPalestrante(String nomeEvento, String cpf) Palestrante
        +removerParticipante(String nomeEvento, String cpf) Resumo
    }

    class Interface {
        <<Main>>
        +main(String[] args)
        -exibirMenuProgressivo(...)
        -criarEvento(...)
        -criarLotes(...)
        -venderIngresso(...)
        -adicionarPalestrante(...)
        -removerPalestrante(...)
        -removerParticipante(...)
        -gerarCertificadoParticipante(...)
        -gerarCertificadoPalestrante(...)
        -listarRelatorioEventos(...)
    }
