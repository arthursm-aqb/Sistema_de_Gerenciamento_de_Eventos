public class Participante extends Pessoa implements Certificado {
    String certificado;
    Participante(String nome, String cpf) {
        super(nome, cpf);
    }

    public void getCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String toString() {
        return certificado;
    }
}
