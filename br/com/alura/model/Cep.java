package br.com.alura.model;

public class Cep {
    private String cep;
    private String url;

    public String getCep() {
        return cep;
    }

    public String getUrl() {
        return url;
    }

    public String buscaCep(String numcep){
        url = "https://viacep.com.br/ws/"+numcep+"/json/";
        return url;
    }
}
