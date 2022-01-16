package com.lehrerkalender.extAPI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class JokesAPIService {

    public JSONObject getJoke() {

        JSONObject joke = null;

        try {
            URL url = new URL("https://geek-jokes.sameerkumar.website/api?format=json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Response code überprüfen
            int responseCode = conn.getResponseCode();

            if(responseCode != 200) {
                throw new RuntimeException("HTTP Response code: " + responseCode);
            }

            StringBuilder information = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNextLine()) {
                information.append(scanner.nextLine());
            }

            scanner.close();

            JSONParser parser = new JSONParser();
            joke = (JSONObject) parser.parse(String.valueOf(information));

            return joke;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
