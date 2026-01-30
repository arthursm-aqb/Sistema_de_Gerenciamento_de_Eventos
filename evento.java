import java.util.List;
import java.util.HashSet;

public class evento{
    
    private final String nomeEvento;
    private HashSet<String> participantes;
    private final HashSet<String> palestrantes;
    private int numParticipantes;
    private final int capacidade;

    public evento(String evento, HashSet<String> palestrantes, int capacidade){

        nomeEvento = evento;
        this.participantes = new HashSet<String>();
        this.palestrantes = palestrantes;
        this.numParticipantes = participantes.size();
        this.capacidade = capacidade;
    }

    public String getEvento(){ return this.nomeEvento;}
    public HashSet<String> getNomeParticipantes(){ return this.participantes;}
    public HashSet<String> getNomePalestrantes(){ return this.palestrantes;}
    public int getNumParticipantes(){ return this.numParticipantes;}
    public int getCapacidade(){ return capacidade;}

    public int setParticipantes(String nome){

        if(!participantes.add(nome)){
            IO.println("Participante já foi cadastrado anteriormente!");
            return -1;
        } else{
            IO.println("Participante cadastrado com sucesso!");
            numParticipantes = participantes.size();
            return 1;
        }

    }

    public boolean setParticipantes(HashSet<String> nomes){

        participantes.addAll(nomes);
        numParticipantes = participantes.size();
        return true;
        
    }

    public boolean removerParticipantes(HashSet<String> nomes){


        if(!participantes.removeAll(nomes)){

            IO.println("Nenhum participante removido...");
            return false;
        }

        IO.println("Participantes removidos com sucesso!");
        numParticipantes = participantes.size();
        return true;

    }

}