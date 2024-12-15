package br.api.laudocs.laudocs_api.exception;

public class ValidationException extends RuntimeException {
    private String mensagem;

    public ValidationException(String mensagem) {
        super(mensagem);
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
