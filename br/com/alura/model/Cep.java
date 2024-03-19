package br.com.alura.model;

import br.com.alura.exception.ExceptionErroCep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Cep {
    private String url;
    public EnderecoRecord buscaCep(String numcep) throws IOException, InterruptedException {
        url = "https://viacep.com.br/ws/"+numcep+"/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.body().contains("erro")){
            throw new ExceptionErroCep("CEP não encontrado");
        }
        String json = response.body();
        Gson gson = new Gson();
        EnderecoRecord objJson = gson.fromJson(json, EnderecoRecord.class);

        return objJson;
    }

    public String cepJson(String numcep) throws IOException, InterruptedException {
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        return json.toJson(this.buscaCep(numcep));
    }
}
