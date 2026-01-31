import java.util.ArrayList;
import java.util.HashSet;

public class evento{
    
    private final String nomeEvento;
    private HashSet<String> participantes;
    private HashSet<String> palestrantes;
    private int numPalestrantes;
    private int numParticipantes;
    private final int capacidade;

    public evento(String evento, HashSet<String> palestrantes, int capacidade){

        nomeEvento = evento;
        this.participantes = new HashSet<String>();
        this.palestrantes = palestrantes;
        this.numParticipantes = participantes.size();
        this.capacidade = capacidade;
        this.numPalestrantes = palestrantes.size();
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

        ArrayList<Integer> par = new ArrayList<>(2);
        par.add(0);
        par.add(tamFinal-tamAtual);

        return par;
        
    }

    public boolean removerParticipantes(HashSet<String> nomes){


        if(!participantes.removeAll(nomes)){

            return false;
        }

        numParticipantes = participantes.size();
        return true;

    }

    public ArrayList<Integer> setPalestrantes(HashSet<String> palestrantes){
        int tamNomes = palestrantes.size();
        int tamAtual = this.palestrantes.size();

        if(!this.palestrantes.addAll(palestrantes)){
            ArrayList<Integer> par = new ArrayList(1);
            par.add(-1);
            return par;
        }

        numPalestrantes = this.palestrantes.size();

        int tamFinal = this.palestrantes.size();

        if(tamNomes+tamAtual != tamFinal){

            ArrayList<Integer> par = new ArrayList<>(2);
            par.add(1);
            par.add(tamFinal-tamAtual);
            return par;
        }

        ArrayList<Integer> par = new ArrayList<>(2);
        par.add(0);
        par.add(tamFinal-tamAtual);

        return par;

    }

    public boolean removerPalestrantes(HashSet<String> nomes){


        if(!palestrantes.removeAll(nomes)){

            return false;
        }

        numPalestrantes = palestrantes.size();
        return true;

    }
}