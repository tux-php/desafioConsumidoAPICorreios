package br.com.alura.model;

import java.io.*;
import java.util.ArrayList;

public class Arquivo {
    public void salvarArquivoJson(ArrayList arquivoJson) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("enderecos.json"));
        writer.write(String.valueOf(arquivoJson));
        writer.close();
    }
    public void lerArquivoJson() throws IOException {
        BufferedReader arquivo = new BufferedReader(new FileReader("enderecos.json"));
        String lerArquivo;
        System.out.println("Lendo arquivo:");
        while((lerArquivo = arquivo.readLine()) != null){
            String[] value = lerArquivo.split("\n");
            for(String val: value){
                System.out.println(val);
            }
        }
        arquivo.close();
    }
}
