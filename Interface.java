import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Interface {

    public static void main(String[] args) {
        painel sistema = new painel();
        Scanner teclado = new Scanner(System.in);
        boolean rodando = true;

        // Lista para bloqueio de remoção
        ArrayList<String> cpfsCertificados = new ArrayList<>(); // Para Participantes
        ArrayList<String> cpfsPalestrantesCertificados = new ArrayList<>(); // Para Palestrantes

        // Estados
        boolean temEvento = false;
        boolean temLote = false;
        boolean temPalestrante = false;
        boolean temVenda = false;
        boolean fluxoCompleto = false;

        System.out.println("=== SISTEMA DE GESTÃO DE EVENTOS ===");

        while (rodando) {

            if(temLote && temPalestrante && temVenda) {
                fluxoCompleto = true;
            }

            exibirMenuProgressivo(temEvento, temLote, temPalestrante, temVenda, fluxoCompleto);

            try {
                System.out.print("Escolha uma opção: ");
                int opcao = teclado.nextInt();
                teclado.nextLine(); // Limpar buffer

                switch (opcao) {
                    case 1 -> {
                        criarEvento(sistema, teclado);
                        temEvento = true;
                    }
                    case 2 -> {
                        if (temEvento) {
                            boolean sucesso = criarLotes(sistema, teclado);
                            if(sucesso) temLote = true;
                        } else System.out.println(">> Bloqueado: Crie um evento primeiro.");
                    }
                    case 3 -> {
                        if (temEvento) {
                            boolean sucesso = adicionarPalestrante(sistema, teclado);
                            if(sucesso) temPalestrante = true;
                        } else System.out.println(">> Bloqueado: Crie um evento primeiro.");
                    }
                    case 4 -> {
                        if (temLote) {
                            boolean vendeu = venderIngresso(sistema, teclado);
                            if(vendeu) temVenda = true;
                        } else System.out.println(">> Bloqueado: Adicione lotes primeiro.");
                    }
                    case 5 -> {
                        if (temVenda) {
                            removerParticipante(sistema, teclado, cpfsCertificados);
                        } else System.out.println(">> Bloqueado: Necessário ter participantes cadastrados.");
                    }
                    case 6 -> {
                        if (temVenda) {
                            gerarCertificadoParticipante(sistema, teclado, cpfsCertificados);
                        } else System.out.println(">> Bloqueado: Realize vendas primeiro.");
                    }
                    case 7 -> {
                        if (temPalestrante) {

                            removerPalestrante(sistema, teclado, cpfsPalestrantesCertificados);
                        } else System.out.println(">> Bloqueado: Adicione palestrantes primeiro.");
                    }
                    case 8 -> {
                        if (temPalestrante) {

                            gerarCertificadoPalestrante(sistema, teclado, cpfsPalestrantesCertificados);
                        } else System.out.println(">> Bloqueado: Adicione palestrantes primeiro.");
                    }
                    case 9 -> {
                        if (fluxoCompleto) {
                            listarRelatorioEventos(sistema);
                        } else System.out.println(">> Bloqueado: Complete o requisitos (Lotes, Palestrantes e Vendas).");
                    }
                    case 0 -> {
                        System.out.println("Encerrando...");
                        rodando = false;
                    }
                    default -> System.out.println("Opção inválida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite apenas números.");
                teclado.nextLine();
            }

            System.out.println("------------------------------------------------");
        }
    }

    private static void exibirMenuProgressivo(boolean evento, boolean lote, boolean palestrante, boolean venda, boolean full) {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Criar Novo Evento");

        if (evento) {
            System.out.println("2. Adicionar Lotes");
            System.out.println("3. Adicionar Palestrante");
        }
        if (lote) {
            System.out.println("4. Vender Ingresso");
        }
        if (venda) {
            System.out.println("5. Remover Participante");
            System.out.println("6. Gerar Certificado Participante");
        }
        if (palestrante) {
            System.out.println("7. Remover Palestrante");
            System.out.println("8. Gerar Certificado Palestrante");
        }
        if (full) {
            System.out.println("9. Relatório Geral");
        }
        System.out.println("0. Sair");
    }

    private static void criarEvento(painel sistema, Scanner sc) {
        System.out.print("Nome do Evento: ");
        String nome = sc.nextLine();
        System.out.print("Capacidade Máxima: ");
        int cap = sc.nextInt();
        sistema.criarEvento(nome, cap);
        System.out.println(">> Evento criado com sucesso!");
    }

    private static boolean criarLotes(painel sistema, Scanner sc) {
        System.out.print("Nome do Evento: ");
        String nome = sc.nextLine();
        ArrayList<Integer> lotes = new ArrayList<>();
        System.out.println("Qtd por lote (-1 para parar):");
        int cont = 1;
        while(true) {
            System.out.print("Lote " + cont + ": ");
            int qtd = sc.nextInt();
            if(qtd == -1) break;
            if(qtd > 0) { lotes.add(qtd); cont++; }
        }
        sc.nextLine();
        if(!lotes.isEmpty()){
            Resumo r = sistema.criarLotes(nome, lotes);
            imprimirResumo(r);
            return (r.codigo() == 0);
        }
        return false;
    }

    private static boolean adicionarPalestrante(painel sistema, Scanner sc) {
        System.out.print("Nome do Evento: ");
        String ev = sc.nextLine();
        System.out.print("Nome Palestrante: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        Resumo r = sistema.adicionarPalestrante(ev, new Palestrante(nome, cpf, esp));
        imprimirResumo(r);
        return (r.codigo() == 30);
    }

    private static boolean venderIngresso(painel sistema, Scanner sc) {
        System.out.print("Nome do Evento: ");
        String ev = sc.nextLine();
        System.out.print("Nome Participante: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        Resumo r = sistema.venderIngresso(ev, 1, new Participante(nome, cpf));
        imprimirResumo(r);
        return (r.codigo() == 23 || r.codigo() == 22);
    }

    private static void gerarCertificadoParticipante(painel sistema, Scanner sc, ArrayList<String> listaBloqueio) {
        System.out.println("--- CERTIFICADO (PARTICIPANTE) ---");
        System.out.print("Nome do Evento: ");
        String ev = sc.nextLine();
        System.out.print("CPF do Participante: ");
        String cpf = sc.nextLine();

        Participante p = sistema.buscarParticipante(ev, cpf);

        if (p != null) {
            int idEvento = sistema.pegarID(ev);
            sistema.getEventos().get(idEvento).gerarCertificado(p);

            if (!listaBloqueio.contains(cpf)) {
                listaBloqueio.add(cpf);
            }
            System.out.println(">> SUCESSO: Certificado gerado");
            System.out.println(">> Doc: " + p.toString());
        } else {
            System.out.println(">> ERRO: Participante não encontrado.");
        }
    }

    private static void gerarCertificadoPalestrante(painel sistema, Scanner sc, ArrayList<String> listaBloqueio) {
        System.out.println("--- CERTIFICADO (PALESTRANTE) ---");
        System.out.print("Nome do Evento: ");
        String ev = sc.nextLine();
        System.out.print("CPF do Palestrante: ");
        String cpf = sc.nextLine();

        Palestrante pal = sistema.buscarPalestrante(ev, cpf);

        if (pal != null) {

            int idEvento = sistema.pegarID(ev);
            sistema.getEventos().get(idEvento).gerarCertificado(pal);

            if (!listaBloqueio.contains(cpf)) {
                listaBloqueio.add(cpf);
            }
            System.out.println(">> SUCESSO: Certificado gerado.");
            System.out.println(">> Doc: " + pal.toString());
        } else {
            System.out.println(">> ERRO: Palestrante não encontrado.");
        }
    }

    private static void removerParticipante(painel sistema, Scanner sc, ArrayList<String> listaBloqueio) {
        System.out.println("--- REMOVER PARTICIPANTE ---");
        System.out.print("Nome do Evento: ");
        String ev = sc.nextLine();
        System.out.print("CPF do Participante: ");
        String cpf = sc.nextLine();

        if (!listaBloqueio.isEmpty() && listaBloqueio.contains(cpf)) {
            System.out.println(">> NEGADO: Participante já possui certificado e não pode ser removido!");
            return;
        }

        Resumo r = sistema.removerParticipante(ev, cpf);
        imprimirResumo(r);
    }

    private static void removerPalestrante(painel sistema, Scanner sc, ArrayList<String> listaBloqueio) {
        System.out.println("--- REMOVER PALESTRANTE ---");
        System.out.print("Nome do Evento: ");
        String ev = sc.nextLine();
        System.out.print("Nome do Palestrante: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        if (!listaBloqueio.isEmpty() && listaBloqueio.contains(cpf)) {
            System.out.println(">> NEGADO: Palestrante já possui certificado e não pode ser removido!");
            return;
        }

        int id = sistema.pegarID(ev);
        if(id != -1) {
            Resumo r = sistema.removerPalestrante(nome, cpf, id);
            imprimirResumo(r);
        } else {
            System.out.println(">> Evento não encontrado.");
        }
    }

    private static void listarRelatorioEventos(painel sistema) {
        System.out.println("\n--- RELATÓRIO GERAL ---");
        var eventos = sistema.getEventos();
        if(eventos.isEmpty()) System.out.println("Vazio.");
        else {
            for(evento e : eventos.values()) {
                System.out.println("Evento: " + e.getEvento() + " | Part: " + e.getNumParticipantes() + " | Pal: " + e.getNumPalestrantes());
            }
        }
    }

    private static void imprimirResumo(Resumo r) {
        if(r.codigo() == 0 || r.codigo() == 23 || r.codigo() == 22 || r.codigo() == 30)
            System.out.println(">> SUCESSO: " + r.mensagem());
        else if (r.codigo() == 21 || r.codigo() == 32)
            System.out.println(">> ATENÇÃO: " + r.mensagem());
        else
            System.out.println(">> ERRO [" + r.codigo() + "]: " + r.mensagem());
    }
}