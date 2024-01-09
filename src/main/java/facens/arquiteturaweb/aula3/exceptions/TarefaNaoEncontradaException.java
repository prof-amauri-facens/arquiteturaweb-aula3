package facens.arquiteturaweb.aula3.exceptions;

public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public TarefaNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

