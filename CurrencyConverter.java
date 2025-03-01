import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            String apiUrl = API_URL + baseCurrency;
            HttpURLConnection connection = (HttpURLConnection) new URI(apiUrl).toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getJSONObject("rates").getDouble(targetCurrency);
        } catch (Exception e) {
            System.out.println("Error fetching exchange rate: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter target currency (e.g., INR): ");
        String targetCurrency = scanner.next().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRate != -1) {
            double convertedAmount = amount * exchangeRate;
            System.out.println("Exchange Rate: 1 " + baseCurrency + " = " + exchangeRate + " " + targetCurrency);
            System.out.println("Converted Amount: " + amount + " " + baseCurrency + " = " + convertedAmount + " " + targetCurrency);
        } else {
            System.out.println("Failed to fetch exchange rate. Please check the currency codes and try again.");
        }

        scanner.close();
    }
}
