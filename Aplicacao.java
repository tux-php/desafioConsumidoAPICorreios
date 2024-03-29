import br.com.alura.exception.ExceptionErroCep;
import br.com.alura.model.Arquivo;
import br.com.alura.model.Cep;
import br.com.alura.model.EnderecoRecord;
import com.google.gson.JsonSyntaxException;
import java.io.*;
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
                    EnderecoRecord url = cep.buscaCep(numcep);
                    String dadosEndereco = url.printEnderecoRcd();

                    String[] listaEndereco = dadosEndereco.split("\n");
                    //Verifica se CEP encontra-se na lista enderecos.
                    if(enderecos.isEmpty() || (!enderecos.contains("CEP: " + cepCorrigido))){
                        for(String end: listaEndereco){
                            enderecos.add(end);
                        }
                        jsonLista.add(cep.cepJson(numcep));
                        System.out.println("CEP: " + cepCorrigido + " adicionado com sucesso!");
                    }else{
                        System.out.println("CEP já cadastrado!");
                    }
                    Arquivo arquivo = new Arquivo();
                    arquivo.salvarArquivoJson(jsonLista);

                    arquivo.lerArquivoJson();

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
