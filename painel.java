import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class painel {

    private HashMap<Integer, HashMap<Integer, Integer>> lotesIngressos; // Armazena um dicionário de um dicionário (ID : (Lote : quantidadeIngressos))
    private int ID; // Armazena a numeração de identificação dos eventos
    private HashMap<Integer, evento> Eventos; // Armazena um dicionário de eventos (ID : Evento)

    // Construtor
    public painel(){

        lotesIngressos = new HashMap<Integer, HashMap<Integer, Integer>>();
        ID = 1;
        this.Eventos = new HashMap<>();
    }

    // Retorna ID do evento se achar algum igual ao nome dado como argumento para função
    public int pegarID(String nomeEvento){

        if(Eventos.isEmpty()) return -2; // Código de retorno que indica que não existe nenhum evento cadastrado

        for(int x : Eventos.keySet()){
            if(Eventos.get(x).getEvento().equals(nomeEvento)){
                return x;
            }
        }

        return -1; // Código de retorno que indica que não foi possível achar um evento com o nome passado como argumento.
    }

    public void criarEvento(){

        Scanner input = new Scanner(System.in);
        IO.println("Digite o nome do evento: ");
        String evento = input.nextLine();
        IO.println("Quantos palestrantes estarão presentes no evento?");
        int numPalestrantes;

        do{
            numPalestrantes = input.nextInt();
            input.nextLine();
            if(numPalestrantes<=0){
                IO.println("Valor menor ou igual à 0 é invalidado. Digite novamente.");
            }
        } while(numPalestrantes<=0);

        HashSet<String> nomePalestrantes = new HashSet<String>();

        if(numPalestrantes==1){
            IO.println("Digite o nome do palestrante: ");
            String palestrante = input.nextLine();
            nomePalestrantes.add(palestrante);
        } else{

            IO.println("Digite o nome dos palestrantes: ");
            for(int i = 0; i < numPalestrantes; i++){
                String palestrante = input.nextLine();
                nomePalestrantes.add(palestrante);
            }
        }

        IO.println("Qual a capacidade do evento?");
        int capacidade;
        do{
            capacidade = input.nextInt();
            input.nextLine();
            if(capacidade<=0){
                IO.println("Capacidade inválida!\nCapacidade válida: (x>0), então digite novamente um valor válido.");
            }

        } while(capacidade<=0);

        Eventos.put(ID, new evento(evento, nomePalestrantes, capacidade));
        ID++;

    }

    public void criarLotes(HashMap<Integer, evento> eventos, Scanner input){

        IO.println("Qual evento você quer definir o lote: ");
        for(evento x : eventos.values()){
            IO.println(x.getEvento());
        }

        String nome = input.nextLine();
        int chave = pegarID(nome);

        if(chave!=-1){

            IO.println("Serão quantos lotes de ingresso do evento '" + nome + "'? ");
            int numLotes = input.nextInt();
            int auxCapacidade = eventos.get(chave).getCapacidade();

            for(int i = 1; i <= numLotes; i++){

                IO.println("Ingressos restantes do evento " + nome + ": " + auxCapacidade);
                IO.println("Digite a quantidade de ingressos do lote " + i + " : ");
                int ingressosLote;
                boolean valorValido = false;

                do{
                    ingressosLote = input.nextInt();

                    if(ingressosLote<=0){
                        IO.println("Valor de ingressos inválido (deve ser maior que 0). Tente novamente.");

                    } else if(ingressosLote>auxCapacidade){
                        IO.println("Valor excede a capacidade restante (" + auxCapacidade + "). Tente novamente:");

                    } else{

                        valorValido = true;
                    }
                } while(!valorValido);

                auxCapacidade -= ingressosLote;
                lotesIngressos.computeIfAbsent(chave, k-> new HashMap<>()).put(i, ingressosLote);

                if (auxCapacidade == 0 && i < numLotes) {
                    IO.println("A capacidade do evento esgotou neste lote!");
                    break;
                }

            }
        }
        
    }

    public int venderIngresso(HashMap<Integer, evento> eventos, Scanner input, HashMap<Integer, HashMap<Integer, Integer>> ingressos){
        // Nome do evento que deseja comprar ingresso

        String nomeEvento = input.nextLine();
        int id = pegarID(nomeEvento);

        // Verifica se o evento existe
        if(id==-1){
            return 10; // Não existe evento com esse nome, então retorna o código 10 (Nome do evento não existe)
        } else if(id==-2){
            return 11; //Não existe nenhum evento cadastrado no momento.
        }

        evento comprar = eventos.get(id); //
        HashMap<Integer, Integer> verificarLotes = ingressos.get(id);


        int quantidadeIngressos = input.nextInt();

        // Verifica se o usuário digitou um valor válido para compra de ingressos
        if(quantidadeIngressos<1){
            return 12; // Código que indica quantidade inválida de ingressos (x>0)
        }

        // Verifica se existe lotes com ingressos suficientes
        int loteValido = -1;
        for(int key : verificarLotes.keySet()){
            if(verificarLotes.get(key)>=quantidadeIngressos){
                loteValido = key;
                break;
            }
        }

        if(loteValido == -1){
            return 13; // Código que indica que não existe mais ingressos para o evento suficientes para quantidade desejada do usuário
        }

        if(quantidadeIngressos==1){
            String nomeParticipante = input.nextLine();
            int validade = Eventos.get(id).setParticipantes(nomeParticipante);
            if(Eventos.get(id).setParticipantes(nomeParticipante)==-1) return 14; // Código que indica que o participante já cadastrado anteriormente
            int deduzirIngressos = ingressos.get(id).get(loteValido);
            verificarLotes.put(loteValido, deduzirIngressos-1);

        } else{
            HashSet<String> nomesParticipantes = new HashSet<>();
            for(int i = 0; i < quantidadeIngressos; i++){
                nomesParticipantes.add(input.nextLine());
            }

            ArrayList<Integer> validade = Eventos.get(id).setParticipantes(nomesParticipantes);
            if(validade.get(0)==-1){
                return 15; // Código que indica que nenhum participante foi cadastrado por algum motivo
            }
            if(validade.get(0)==1){

                int deduzirIngressos = ingressos.get(id).get(loteValido);
                deduzirIngressos = deduzirIngressos - validade.get(1);
                verificarLotes.put(loteValido, deduzirIngressos);
                return 21; // Código que indica que nem todos os participantes foram adicionados
            }


            int deduzirIngressos = ingressos.get(id).get(loteValido);
            deduzirIngressos = deduzirIngressos - quantidadeIngressos;
            verificarLotes.put(loteValido, deduzirIngressos);
            return 22; // Código que indica que todos os participantes foram adicionados com sucesso;

        }


        // Se existe, então deduz do lote atual a quantidade de ingressos

        return 23; // Código que indica que o participante foi cadastrado com sucesso
    }


}


