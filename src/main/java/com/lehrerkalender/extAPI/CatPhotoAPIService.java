package com.lehrerkalender.extAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class CatPhotoAPIService {

    public JSONObject getRandomCatPhoto() {

        JSONObject catPhoto = null;

        try {
            URL url = new URL("https://thatcopy.pw/catapi/rest/");

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

            //von der API wird nur ein Objekt geliefert
            catPhoto = parseToJSONObject(information);

            return catPhoto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //Helfer-Methode zum parsen zu einem JSONObjekt
    private JSONObject parseToJSONObject(StringBuilder information) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject parsedObject = (JSONObject) parser.parse(String.valueOf(information));
        return parsedObject;
    }

    //Helfer-Methode zum parsen zu einem JSONArray, wenn mehrere Einträge von der externen API geliefert werden
    private JSONArray parseToJSONArray(StringBuilder information) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray dataObject = (JSONArray) parser.parse(String.valueOf(information));
        //in der aufrufenden Methode muss dann folgendes aufgerufen werden:
        //JSONObject object = (JSONObject) dataObject.get(i);
        return dataObject;
    }
}
