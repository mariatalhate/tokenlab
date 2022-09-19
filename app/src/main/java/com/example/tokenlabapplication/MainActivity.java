package com.example.tokenlabapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Filme;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);


        List<Filme> lista = new ArrayList<>();
            try {
                URL url = new URL("https://desafio-mobile.nyc3.digitaloceanspaces.com/movies-v2/240");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                // checar se conectou
                int responseCode = conn.getResponseCode();
                // codigo 200 = conectou
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode: "+ responseCode);
                } else {
                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                    scanner.close();
                    System.out.println(informationString);

                    JSONParser parse = new JSONParser();
                    JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));


                    // adicionar cada objeto do array json na lista de objetos filme da classe banco
                    int ArraySize = dataObject.length();
                    for (int i = 0; i < ArraySize; i++) {
                        JSONObject movie = (JSONObject) dataObject.get(i);
                        Filme novoFilme= new Filme();
                        novoFilme.setId(movie.get("id").toString());
                        novoFilme.setPoster_url(movie.get("poster_url").toString());
                        novoFilme.setVote_average(movie.get("vote_average").toString());
                        novoFilme.setTitle(movie.get("title").toString());
                        novoFilme.setGenres(movie.get("genres"));
                        novoFilme.setRelease_date(movie.get("release_date").toString());
                        lista.add(novoFilme);

                    }

                }
            } catch(Exception e) {
                e.printStackTrace();
            }
    }
}