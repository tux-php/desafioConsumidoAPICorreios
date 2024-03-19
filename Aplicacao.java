import br.com.alura.exception.ExceptionErroCep;
import br.com.alura.model.Cep;
import br.com.alura.model.EnderecoRecord;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> enderecos = new ArrayList<>();
        ArrayList<String> jsonLista = new ArrayList<>();

        boolean sair = false;
        while(!sair){
            System.out.println("Gostaria de pesquisar CEP (S/N) ?:");
            String resp = scan.next();
            if(resp.equalsIgnoreCase("s")){
                try{
                    System.out.println("Informe nº do CEP:");
                    String numcep = scan.next();
                    String cepCorrigido = adicionarHifenNoCep(numcep);

                    Cep cep = new Cep();
                    String url = cep.buscaCep(numcep);
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

                    Gson gson = new Gson();
                    EnderecoRecord objJson = gson.fromJson(json, EnderecoRecord.class);
                    String dadosEndereco = objJson.printEnderecoRcd();


                    String[] quebraEndereco = dadosEndereco.split("\n");
                    if(enderecos.isEmpty() || (!enderecos.contains("CEP: " + cepCorrigido))){
                        for(String end: quebraEndereco){
                            enderecos.add(end);
                        }

                        jsonLista.add(json);
                        System.out.println("CEP: " + cepCorrigido + " adicionado com sucesso!");
                    }else{
                        System.out.println("CEP já cadastrado.");
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter("enderecos.txt"));
                    writer.write(String.valueOf(jsonLista));
                    writer.close();

                    System.out.println(enderecos);

                    BufferedReader arquivo = new BufferedReader(new FileReader("enderecos.txt"));
                    String lerArquivo;
                    System.out.println("Lendo arquivo:");
                    while((lerArquivo = arquivo.readLine()) != null){
                        String[] value = lerArquivo.split("\n");
                        for(String val: value){
                            System.out.println(val);
                        }
                    }
                    arquivo.close();
                }catch (ExceptionErroCep | IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }catch (JsonSyntaxException e){
                    System.out.println("Erro na sintaxe do Json: " + e.getMessage());
                }catch(Exception e){
                    System.out.println("Ocorreu um erro: " + e.getMessage());
                }
            }else{
                sair = true;
            }
        }
    }

    private static String adicionarHifenNoCep(String numcep) {
        String[] tratarcep = numcep.split("");
        ArrayList<String> dadoscep = new ArrayList<>();
        for(String cep : tratarcep){
            dadoscep.add(cep);
        }
        dadoscep.add(5,"-");
        String stringcep = "";
        for(String cep: dadoscep){
            stringcep += cep;
        }
        return stringcep;
    }
}
