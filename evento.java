import java.util.ArrayList;
import java.util.HashSet;

public class evento{
    
    private final String nomeEvento;
    private HashSet<Participante> participantes;
    private HashSet<Palestrante> palestrantes;
    private int numPalestrantes;
    private int numParticipantes;
    private final int capacidade;

    public evento(String evento, int capacidade){

        nomeEvento = evento;
        this.capacidade = capacidade;
        this.participantes = new HashSet<Participante>();
        this.palestrantes = new HashSet<Palestrante>();
        this.numPalestrantes = 0;
        this.numParticipantes = 0;

    }

    public String getEvento(){ return this.nomeEvento;}
    public HashSet<Participante> getNomeParticipantes(){ return this.participantes;}
    public HashSet<Palestrante> getNomePalestrantes(){ return this.palestrantes;}
    public int getNumParticipantes(){ return this.numParticipantes;}
    public int getNumPalestrantes(){ return this.numPalestrantes;}
    public int getCapacidade(){ return capacidade;}

    //
    public int adicionarParticipantes(Participante unico){

        if(!participantes.add(unico)){
            return -1;
        } else{
            numParticipantes = participantes.size();
            return 1;
        }

    }

    public ArrayList<Integer> adicionarParticipantes(HashSet<Participante> nomes){

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

    public boolean removerParticipantes(HashSet<Participante> listaParticipantes){


        if(!participantes.removeAll(listaParticipantes)){

            return false;
        }

        numParticipantes = participantes.size();
        return true;

    }



    public ArrayList<Integer> setPalestrantes(HashSet<Palestrante> listaPalestrantes){
        int tamNomes = palestrantes.size();
        int tamAtual = this.palestrantes.size();

        if(!this.palestrantes.addAll(listaPalestrantes)){
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

    public boolean removerPalestrante(Palestrante palestrante){


        if(!palestrantes.remove(palestrante)){

            return false;
        }

        numPalestrantes = palestrantes.size();
        return true;

    }

    public void setCertificado(Participante participante){
        participante.getCertificado("Certificado de comparecimento do evento "+getEvento()+ " ao participante "+ participante.getNome()+"!");
    }
}