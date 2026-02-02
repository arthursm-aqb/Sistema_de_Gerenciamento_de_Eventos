public class Palestrante extends Pessoa{
    private String especialidade;
    public Palestrante(String nome, String cpf, String especialidade) {
        super(nome, cpf);
        this.especialidade = especialidade;
    }
}
