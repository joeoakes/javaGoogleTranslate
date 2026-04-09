/*
Go to Google Cloud Console: Open: https://console.cloud.google.com
Click “APIs & Services”
Click “Library”
Cloud Translation API
Click on Enable
Create Credentials → API Key
Save off the Key to paste in
*/

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GoogleTranslateREST {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the API Key: ");
        String apiKey = sc.nextLine();

        System.out.print("Enter the plainttext: ");
        String text = sc.nextLine();

        String urlStr = "https://translation.googleapis.com/language/translate/v2"
                + "?key=" + apiKey;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String jsonInput = "{ \"q\": \"" + text + "\", \"target\": \"ar\" }";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"));

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line.trim());
        }

        System.out.println(response.toString());
    }
}