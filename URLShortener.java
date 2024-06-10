import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Scanner;

public class URLShortener {

    // Main method to handle user input and invoke the URL shortening function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Prompt the user to enter a long URL
            System.out.println("Enter the long URL:");
            String longUrl = scanner.nextLine();

            // Call the shortenURL method to get the shortened URL
            String shortUrl = shortenURL(longUrl);

            // Print the shortened URL
            System.out.println("Shortened URL: " + shortUrl);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Method to shorten a given long URL using the TinyURL API
    public static String shortenURL(String longUrl) throws IOException, URISyntaxException {
        String apiUrl = "https://tinyurl.com/api-create.php";

        // Construct the request URL with parameters using URI
        URI uri = new URI(apiUrl + "?url=" + URLEncoder.encode(longUrl, "UTF-8"));

        // Open a connection to the TinyURL API
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");

        // Read the response from the API
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            // Return the shortened URL
            return response.toString().trim();
        } finally {
            // Disconnect the connection
            conn.disconnect();
        }
    }
}