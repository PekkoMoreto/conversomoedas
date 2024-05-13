import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;

public class ConversorMoedas {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        exibirMenu();
    }

    public static void exibirMenu() {
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        System.out.println("\nSelecione a opção desejada:");
        System.out.println("1. USD para EUR");
        System.out.println("2. EUR para USD");
        System.out.println("3. USD para BRL");
        System.out.println("4. BRL para USD");
        System.out.println("5. EUR para BRL");
        System.out.println("6. BRL para EUR");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int opcao = Integer.parseInt(reader.readLine());
            switch (opcao) {
                case 1:
                    converterMoeda("USD", "EUR");
                    break;
                case 2:
                    converterMoeda("EUR", "USD");
                    break;
                case 3:
                    converterMoeda("USD", "BRL");
                    break;
                case 4:
                    converterMoeda("BRL", "USD");
                    break;
                case 5:
                    converterMoeda("EUR", "BRL");
                    break;
                case 6:
                    converterMoeda("BRL", "EUR");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void converterMoeda(String moedaOrigem, String moedaDestino) {
        try {
            String apiKey = "3bb4c2def167073169acb1e6";
            URL url = new URL(API_URL + moedaOrigem);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            Gson gson = new Gson();
            MoedaData data = gson.fromJson(response.toString(), MoedaData.class);

            double taxaConversao = data.rates.get(moedaDestino);
            System.out.println("Taxa de conversão de " + moedaOrigem + " para " + moedaDestino + ": " + taxaConversao);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MoedaData {
        Map<String, Double> rates;
    }
}
