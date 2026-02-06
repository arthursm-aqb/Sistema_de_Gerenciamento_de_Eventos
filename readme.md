## Este projeto da faculdade é uma aplicação em Java desenvolvida para gerenciamento de eventos. O projeto foi desenvolvido na disciplina de Programação Orientada a Objetos com finalidade de concretizar todos os ensinamentos do semestre de 2025.2 dos conceitos fundamentais de Orientação a Objetos.

## Funcionalidades Principais

- Gestão de Eventos: Criação de eventos definindo nome e capacidade máxima.
- Controle de Lotes: Criação de múltiplos lotes de ingressos com validação para não exceder a capacidade do evento.
- Venda de Ingressos: Cadastro de participantes com validação.
- Gestão de Palestrantes: Cadastro de palestrantes com especialidades e remoção.
- Sistema de Certificação: Geração de certificados via polimorfismo para Participantes e Palestrantes. Além disso, uma vez que um certificado é gerado, o sistema impede a remoção da pessoa do banco de dados, garantindo a integridade do evento.
- Relatório simplificado: Visualização do status geral dos eventos cadastrados no terminal.

## Conceitos usados no projeto

- Herança: As classes Participante e Palestrante herdam atributos comuns (nome, CPF) da classe abstrata Pessoa.
- Polimorfismo: Sobrecarga de métodos para gerar certificados específicos dependendo do tipo de pessoa (Participante ou Palestrante).
- Interfaces: A interface Certificado define o contrato para recebimento do documento.
- Java Records: Utilização do record Resumo para retornar códigos de status e mensagens imutáveis entre o painel e a Interface.
- Coleções: HashMap (indexação rápida de eventos e lotes), HashSet(garantir que não existam participantes ou palestrantes duplicados no mesmo evento) e ArrayList (listas de segurança - bloqueio de remoção).


## Modo de utilização

# O usuário é apresentado ao sistema com um menu progressivo, várias funcionalidades precisam ser desbloqueadas, dessa forma evita do usuário cometer erros que podem comprometer o sistema. A primeira tela ao usuário é uma com a opção de criar um novo evento e, depois da criação de um evento, liberam-se adicionar lotes de ingresso e adicionar Palestrante. Por fim, om lotes criados, libera-se a venda de Ingressos, que libera remover participantes e gerar certificados. Assim, o relatório geral fica disponível após o fluxo básico ser concluído. Além disso, Ao gerar um certificado, o CPF é adicionado a uma lista de bloqueio e a remoção desse CPF torna-se proibida.


