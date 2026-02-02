import java.util.ArrayList;
import java.util.HashSet;

public class evento{
    
    private final String nomeEvento;
    private HashSet<Participante> participantes;
    private HashSet<Palestrante> palestrantes;
    private int numPalestrantes;
    private int numParticipantes;
    private final int capacidade;

    // Construtor. Inicializa as listas de participantes e palestrantes e define a capacidade.
    public evento(String evento, int capacidade){

        nomeEvento = evento;
        this.capacidade = capacidade;
        this.participantes = new HashSet<Participante>();
        this.palestrantes = new HashSet<Palestrante>();
        this.numPalestrantes = 0;
        this.numParticipantes = 0;

    }

    // Retorna o nome do evento
    public String getEvento(){ return this.nomeEvento;}

    // Retorna o conjunto de participantes cadastrados.
    public HashSet<Participante> getNomeParticipantes(){ return this.participantes;}

    // Retorna o conjunto (HashSet) de palestrantes cadastrados.
    public HashSet<Palestrante> getNomePalestrantes(){ return this.palestrantes;}

    // Retorna a quantidade atual de participantes.
    public int getNumParticipantes(){ return this.numParticipantes;}

    // Retorna a quantidade atual de palestrantes.
    public int getNumPalestrantes(){ return this.numPalestrantes;}

    // Retorna a capacidade máxima do evento.
    public int getCapacidade(){ return capacidade;}

    // Adiciona um único participante ao evento. 1 = sucesso (participante adicionado) e -1 = falha (participante já estava cadastrado).
    public int adicionarParticipantes(Participante unico){

        if(!participantes.add(unico)){
            return -1;
        } else{
            numParticipantes = participantes.size();
            return 1;
        }

    }

    // Adiciona um lote de participantes ao evento e verifica duplicatas automaticamente via HashSet. Index [0]: (-1 = todos já existiam/falha, 1 = parcialmente adicionados, 0 = todos adicionados com sucesso) e Index [1]: quantidade de novos participantes efetivamente cadastrados (presente apenas se index[0] for 0 ou 1).
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

    // Remove um grupo de participantes do evento.
    public boolean removerParticipantes(HashSet<Participante> listaParticipantes){


        if(!participantes.removeAll(listaParticipantes)){

            return false;
        }

        numParticipantes = participantes.size();
        return true;

    }

    // Adiciona um grupo de palestrantes ao evento.
    public ArrayList<Integer> adicionarPalestrantes(HashSet<Palestrante> listaPalestrantes){
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

    // Remove um palestrante específico do evento.
    public boolean removerPalestrante(Palestrante palestrante){


        if(!palestrantes.remove(palestrante)){

            return false;
        }

        numPalestrantes = palestrantes.size();
        return true;

    }

    // Gera e atribui o texto do certificado para um participante específico.
    public void gerarCertificado(Participante participante){
        participante.getCertificado("Certificado de comparecimento do evento "+getEvento()+ " ao participante "+ participante.getNome()+"!");
    }

    // Gera e atribui o texto do certificado para um palestrante específico.
    public void gerarCertificado(Palestrante palestrante){
        palestrante.getCertificado("Certificado de participação como palestrante do evento " + getEvento() + " ao Sr. || Sra. " + palestrante.getNome()+" de especialidade " + palestrante.getEspecialidade()+ "!");
    }
}