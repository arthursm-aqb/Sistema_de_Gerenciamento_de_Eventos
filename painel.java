import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class painel {

    private HashMap<Integer, HashMap<Integer, Integer>> lotesIngressos; // Armazena um dicionário de um dicionário (ID : (Lote : quantidadeIngressos))
    private int ID; // Armazena a numeração de identificação dos eventos
    private HashMap<Integer, evento> Eventos; // Armazena um dicionário de eventos (ID : Evento)
    private evento getEvento(int ID){
        if(!Eventos.containsKey(ID)){ return null;}
        return Eventos.get(ID);
    } // Retorna um objeto evento com base na ID

    // Construtor
    public painel(){

        lotesIngressos = new HashMap<Integer, HashMap<Integer, Integer>>();
        ID = 1;
        this.Eventos = new HashMap<>();
    }

    // Retorna um map com os eventos cadastrados
    public HashMap<Integer, evento> getEventos() { return Eventos;}

    // Retorna um map de um map com os lotes e ingressos dos eventos
    public HashMap<Integer, HashMap<Integer, Integer>> getLotesIngressos() { return lotesIngressos;}

    // Busca o ID de um evento baseado no nome fornecido.
    public int pegarID(String nomeEvento){

        if(Eventos.isEmpty()) return -2; // Código de retorno que indica que não existe nenhum evento cadastrado

        for(int x : Eventos.keySet()){
            if(Eventos.get(x).getEvento().equals(nomeEvento)){
                return x;
            }
        }

        return -1; // Código de retorno que indica que não foi possível achar um evento com o nome passado como argumento.
    }

    // Cria um novo objeto Evento e o armazena no mapa de eventos. O ID é gerado automaticamente de forma incremental.
    public void criarEvento(String nomeEvento, int capacidade){

        evento novo = new evento(nomeEvento, capacidade);
        Eventos.put(ID, novo);
        ID++;

    }

    // Cria ou adiciona lotes de ingressos a um evento existente. Realiza a validação para garantir que a soma dos lotes não ultrapasse a capacidade total do evento.
    public int criarLotes(String nomeEvento, ArrayList<Integer> ingressosLotes){

        int chave = pegarID(nomeEvento);

        if(chave!=-1){

            evento temp = getEvento(chave);

            if(!lotesIngressos.containsKey(chave)){
                int sum =  0;

                for(int i : ingressosLotes){
                    sum += i;
                }

                if(sum>temp.getCapacidade()){
                    return -1; // O número de ingresoss ultrapassam a capacidade do evento
                }

                for(int i = 1; i<=ingressosLotes.size(); i++){
                    lotesIngressos.computeIfAbsent(chave, k-> new HashMap<>()).put(i, ingressosLotes.get(i-1));
                }
            } else{

                HashMap<Integer, Integer> tempLotes = lotesIngressos.get(chave);
                int sum = 0;
                for(int x : tempLotes.values()){
                    sum += x;
                }

                for(int i : ingressosLotes){
                    sum += i;
                }

                if(sum>temp.getCapacidade()){
                    return -1; // O número de ingresoss ultrapassam a capacidade do evento
                }

                int index = tempLotes.size()+1;

                for(int i : ingressosLotes){
                    tempLotes.put(index, i);
                    index++;
                }

            }

        } else{
            return -2; // Evento não existe
        }

        return 0; // Lote criado com sucesso

    }

    // Realiza a venda de ingresso para um único participante. Verifica disponibilidade nos lotes, cadastra o participante e desconta do estoque.
    public int venderIngresso(String nomeEvento, int quantidadeIngresso, Participante participante) {

        // Verifica se o evento existe
        int id = pegarID(nomeEvento);

        if(id==-1){
            return 10; // Não existe evento com esse nome, então retorna o código 10 (Nome do evento não existe)
        } else if(id==-2){
            return 11; //Não existe nenhum evento cadastrado no momento.
        }

        HashMap<Integer, Integer> verificarLotes = lotesIngressos.get(id);

        if(verificarLotes==null){
            return 16; // Código que indica que Lotes é nulo
        }

        // Verifica se o usuário digitou um valor válido para compra de ingressos
        if(quantidadeIngresso<1 || quantidadeIngresso>1){
            return 12; // Código que indica quantidade inválida de ingressos (x>0)
        }

        // Verifica se existe lotes com ingressos suficientes
        int loteValido = -1;
        for(int key : verificarLotes.keySet()){
            if(verificarLotes.get(key)>=quantidadeIngresso){
                loteValido = key;
                break;
            }
        }

        if(loteValido == -1){
            return 13; // Código que indica que não existe mais ingressos para o evento suficientes para quantidade desejada do usuário
        }


        int validade = Eventos.get(id).adicionarParticipantes(participante);
        if(validade==-1) return 14; // Código que indica que o participante já cadastrado anteriormente
        int deduzirIngressos = lotesIngressos.get(id).get(loteValido);
        verificarLotes.put(loteValido, deduzirIngressos-1);


        // Se existe, então deduz do lote atual a quantidade de ingressos
        return 23; // Código que indica que o participante foi cadastrado com sucesso

    }

    // Realiza a venda de ingressos para uma lista de participantes. Gerencia casos onde alguns participantes já podem estar cadastrados.
    public int venderIngresso(String nomeEvento, int quantidadeIngresso, HashSet<Participante> participantes) {

        // Verifica se o evento existe
        int id = pegarID(nomeEvento);

        if(id==-1){
            return 10; // Não existe evento com esse nome, então retorna o código 10 (Nome do evento não existe)
        } else if(id==-2){
            return 11; //Não existe nenhum evento cadastrado no momento.
        }

        HashMap<Integer, Integer> verificarLotes = lotesIngressos.get(id);

        if(verificarLotes==null){
            return 16; // Código que indica que Lotes é nulo
        }

        // Verifica se o usuário digitou um valor válido para compra de ingressos
        if(quantidadeIngresso<1 || quantidadeIngresso==1){
            return 12; // Código que indica quantidade inválida de ingressos (x>0)
        }

        // Verifica se existe lotes com ingressos suficientes
        int loteValido = -1;
        for(int key : verificarLotes.keySet()){
            if(verificarLotes.get(key)>=quantidadeIngresso){
                loteValido = key;
                break;
            }
        }

        if(loteValido == -1){
            return 13; // Código que indica que não existe mais ingressos para o evento suficientes para quantidade desejada do usuário
        }



        ArrayList<Integer> validade = Eventos.get(id).adicionarParticipantes(participantes);
        if(validade.get(0)==-1){
            return 15; // Código que indica que nenhum participante foi cadastrado por algum motivo
        }
        if(validade.get(0)==1){

            int deduzirIngressos = lotesIngressos.get(id).get(loteValido);
            deduzirIngressos = deduzirIngressos - validade.get(1);
            verificarLotes.put(loteValido, deduzirIngressos);
            return 21; // Código que indica que nem todos os participantes foram adicionados
        }


        int deduzirIngressos = lotesIngressos.get(id).get(loteValido);
        deduzirIngressos = deduzirIngressos - quantidadeIngresso;
        verificarLotes.put(loteValido, deduzirIngressos);
        return 22; // Código que indica que todos os participantes foram adicionados com sucesso;

    }

    // Adiciona uma lista de palestrantes a um evento.
    public int adicionarPalestrantes(String nomeEvento, HashSet<Palestrante> palestrantes) {

        int id = pegarID(nomeEvento);

        if(id == -1) return 10; // Evento não encontrado
        if(id == -2) return 11; // Sem eventos cadastrados

        ArrayList<Integer> resultado = Eventos.get(id).adicionarPalestrantes(palestrantes);

        int status = resultado.getFirst();

        if (status == -1) {
            return 31; // Nenhum palestrante adicionado (todos já existiam)
        } else if (status == 1) {
            return 32; // Sucesso Parcial (alguns foram adicionados, outros já existiam)
        }

        return 30; // Sucesso Total (todos adicionados)
    }

    // Adiciona um único palestrante ao evento.
    public int adicionarPalestrante(String nomeEvento, Palestrante palestrante) {
        int id = pegarID(nomeEvento);

        if(id == -1) return 10; // Evento não encontrado
        if(id == -2) return 11; // Sem eventos cadastrados


        HashSet<Palestrante> temp = new HashSet<>();
        temp.add(palestrante);

        ArrayList<Integer> resultado = Eventos.get(id).adicionarPalestrantes(temp);

        // Mapeia o retorno do ArrayList para códigos de controle do Painel
        if (resultado.getFirst() == -1) {
            return 31; // Código indicando que o palestrante já existe
        }

        return 30; // Código indicando Sucesso
    }

    // Remove um palestrante do evento baseando-se no Nome e CPF.
    public int removerPalestrante(String nome, String cpf, int id){

        evento tempEvento = null;
        tempEvento = Eventos.get(id);

        if(tempEvento==null){ return 40;} // Evento inválido

        HashSet<Palestrante> tempPalestrante = tempEvento.getNomePalestrantes();
        Palestrante remover = null;
        for(Palestrante p : tempPalestrante){
            if(p.getNome().equals(nome) && p.getCpf().equals(cpf)){
                remover = p;
                break;
            }
        }

        if(remover == null){ return 41;} // Palestrante não existe

        if(!tempEvento.removerPalestrante(remover)){ return 42;} // Não foi possível remover o palestrante

        return 0; // Palestrante removido com sucesso
    }

}


