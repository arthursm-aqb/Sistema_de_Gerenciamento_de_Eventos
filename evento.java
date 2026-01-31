import java.util.ArrayList;
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

    //
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

    public ArrayList<Integer> setParticipantes(HashSet<String> nomes){

        int tamNomes = nomes.size();
        int tamAtual = participantes.size();

        if(!participantes.addAll(nomes)){
            ArrayList<Integer> par = new ArrayList(1);
            par.add(-1);
            return par;
        }

        numParticipantes = participantes.size();

        int tamFinal = participantes.size();

        if(tamNomes+tamAtual != tamFinal){

            ArrayList<Integer> par = new ArrayList<>(2);
            par.add(1);
            par.add(tamFinal-tamAtual);
            return par;
        }

        numParticipantes = participantes.size();
        ArrayList<Integer> par = new ArrayList<>(2);
        par.add(0);
        par.add(tamFinal-tamAtual);

        return par;
        
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