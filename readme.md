## Este projeto da faculdade é uma aplicação em Java desenvolvida para gerenciamento de eventos. O projeto foi desenvolvido na disciplina de Programação Orientada a Objetos com finalidade de concretizar todos os ensinamentos do semestre de 2025.2 dos conceitos fundamentais de Orientação a Objetos.

## Funcionalidades Principais

# - Gestão de Eventos: Criação de eventos definindo nome e capacidade máxima.
# - Controle de Lotes: Criação de múltiplos lotes de ingressos com validação para não exceder a capacidade do evento.
# - Venda de Ingressos: Cadastro de participantes com validação.
# - Gestão de Palestrantes: Cadastro de palestrantes com especialidades e remoção.
# - Sistema de Certificação: Geração de certificados via polimorfismo para Participantes e Palestrantes. Além disso, uma vez que um certificado é gerado, o sistema impede a remoção da pessoa do banco de dados, garantindo a integridade do evento.
# - Relatório simplificado: Visualização do status geral dos eventos cadastrados no terminal.

## Conceitos usados no projeto:

# Herança: As classes Participante e Palestrante herdam atributos comuns (nome, CPF) da classe abstrata Pessoa.
# Polimorfismo: Sobrecarga de métodos para gerar certificados específicos dependendo do tipo de pessoa (Participante ou Palestrante).
# Interfaces: A interface Certificado define o contrato para recebimento do documento.
# Java Records: Utilização do record Resumo para retornar códigos de status e mensagens imutáveis entre o painel e a Interface.
# Coleções: HashMap (indexação rápida de eventos e lotes), HashSet(garantir que não existam participantes ou palestrantes duplicados no mesmo evento) e ArrayList (listas de segurança - bloqueio de remoção).
