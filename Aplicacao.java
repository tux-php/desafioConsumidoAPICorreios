import br.com.alura.exception.ExceptionErroCep;
import br.com.alura.model.EnderecoRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        boolean sair = false;
        while(!sair){
            System.out.println("Gostaria de pesquisar CEP (S/N) ?:");
            String resp = scan.next();
            if(resp.equalsIgnoreCase("s")){
                try{
                    System.out.println("Informe nº do CEP:");
                    String numcep = scan.next();
                    String url = "https://viacep.com.br/ws/"+numcep+"/json/";
                    System.out.println(url);

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
                    System.out.println("Como vêm:");
                    System.out.println(json);

                    Gson gson = new Gson();
                    EnderecoRecord objJson = gson.fromJson(json, EnderecoRecord.class);
                    System.out.println(objJson.bairro());
                    System.out.println("Obj java");
                    System.out.println(objJson);


                }catch (ExceptionErroCep e){
                    System.out.println(e.getMessage());
                }
            }else{
                sair = true;
            }
        }
    }
}
