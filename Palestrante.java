public class Palestrante extends Pessoa implements Certificado{
    private String especialidade;
    private String certificado;
    public Palestrante(String nome, String cpf, String especialidade) {
        super(nome, cpf);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void getCertificado(String certificado){
        this.certificado = certificado;
    }

    public String toString(){
        return certificado;
    }
}
