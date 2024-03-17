package br.com.alura.exception;

public class ExceptionErroCep extends RuntimeException{
    String msg;
    public ExceptionErroCep(String msg){
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
