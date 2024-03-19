package br.com.alura.model;

public record EnderecoRecord(String cep, String logradouro, String complemento,
                             String bairro, String localidade, String uf, String ddd) {
    public String printEnderecoRcd(){
        String dadosEndereco = "Dados do Endereco\n";
        dadosEndereco += "CEP: "+cep()+"\n";
        dadosEndereco += "Logradouro: "+logradouro()+"\n";
        dadosEndereco += "Complemento: "+complemento()+"\n";
        dadosEndereco += "Bairro: "+bairro()+"\n";
        dadosEndereco += "Localidade: "+localidade()+"\n";
        dadosEndereco += "UF: "+uf()+"\n";

        return dadosEndereco;
    }
}
