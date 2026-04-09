/*
Go to Google Cloud Console: Open: https://console.cloud.google.com
Click “APIs & Services”
Click “Library”
Search for: Cloud Translation API
Click on Enable
Create Credentials → API Key
Save the API Key to paste into the program
*/

import java.io.*;                  // For input/output streams
import java.net.HttpURLConnection; // For HTTP connection handling
import java.net.URL;               // For URL object
import java.util.Scanner;          // For user input

public class GoogleTranslateREST {

    public static void main(String[] args) throws Exception {

        // Create Scanner to read user input from keyboard
        Scanner sc = new Scanner(System.in);

        // Prompt user for Google API key
        System.out.print("Enter the API Key: ");
        String apiKey = sc.nextLine();

        // Prompt user for text to translate
        System.out.print("Enter the plaintext: ");
        String text = sc.nextLine();

        // Build the request URL with API key
        String urlStr = "https://translation.googleapis.com/language/translate/v2"
                + "?key=" + apiKey;

        // Create URL object
        URL url = new URL(urlStr);

        // Open HTTP connection to the API endpoint
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set request method to POST (required by API)
        conn.setRequestMethod("POST");

        // Enable sending data in request body
        conn.setDoOutput(true);

        // Set content type to JSON
        conn.setRequestProperty("Content-Type", "application/json");

        // Create JSON request body
        // q = text to translate
        // target = target language (ar = Arabic)
        String jsonInput = "{ \"q\": \"" + text + "\", \"target\": \"ar\" }";

        // Send JSON data to API
        try (OutputStream os = conn.getOutputStream()) {
            // Convert string to bytes using UTF-8 encoding
            os.write(jsonInput.getBytes("utf-8"));
        }

        // Read response from API (input stream)
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"));

        // Store response text
        StringBuilder response = new StringBuilder();
        String line;

        // Read response line by line
        while ((line = br.readLine()) != null) {
            response.append(line.trim());
        }

        // Output full JSON response
        System.out.println(response.toString());

        // NOTE:
        // Response will look like:
        // {
        //   "data": {
        //     "translations": [
        //       { "translatedText": "..." }
        //     ]
        //   }
        // }
    }
}