package com.example.android.amor_em_leite;


import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Conexao {

    public static String postDados(String urlUsuario, String parametrosUsuario) {
        URL url;
        HttpURLConnection connection = null; //responsavel por fazer a conexao

        try{
            url = new URL(urlUsuario);
            //realizando a conexao, abrindo a conexao pelo endereço digitado pelo usuário
            connection = (HttpURLConnection) url.openConnection();

            //especificar para conexao qual o método que eu quero enviar as informações
            connection.setRequestMethod("POST"); //aqui o método é POST

            //aqui o Content-Type vai especificar para a conexao como que queremos que os dados sejam codificados no envio
            //e o servidor vai saber como usar e separar esses dados corretamente
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlenconded");

            //nº de bytes que vai ser enviado (tam da informação) para conexao e transformar para String na forma correta
            connection.setRequestProperty("Content-Lenght", "" + Integer.toString(parametrosUsuario.getBytes().length));

            connection.setRequestProperty("Content-Language", "pt-BR");

            connection.setUseCaches(false);//não armazenar nenhum dado no dispositivo, conexao em tempo real
            connection.setDoInput(true);//habilitando entrada de dados
            connection.setDoOutput(true);//habilitando saída de dados


            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(parametrosUsuario);
            outputStreamWriter.flush();

            //obter a informação
            InputStream inputStream  = connection.getInputStream();
            //pegar os dados e colocar no banco
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String linha;
            StringBuffer resposta = new StringBuffer();

            //while para pegar linha por linha, enquanto tiver informação quero que ele fique lendo
            while ((linha = bufferedReader.readLine()) !=null){
                resposta.append(linha); //o append junta linha com linha, juntando pelo final da outra
                resposta.append('\r'); //o append junta uma string com a final da outra
            }

            bufferedReader.close();
            return resposta.toString();

        }catch (Exception erro){

            return null;

        }finally {
            //se a conexao tiver ocorrido (!=null) eu quero que ela desconecte(.disconnect())
            if(connection != null){
                connection.disconnect();
            }

        }
    }

}
