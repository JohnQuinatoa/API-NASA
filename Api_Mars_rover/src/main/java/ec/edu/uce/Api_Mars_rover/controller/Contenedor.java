package ec.edu.uce.Api_Mars_rover.controller;

import ec.edu.uce.Api_Mars_rover.model.Images;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Contenedor {

    private final String API_KEY = "qUEDfDWbauqlEdqpJ6666QysAwWneGocNfrCF1mb";
    private List<Images> images = new ArrayList<>();

    public Contenedor() {

    }

    public void obtenerUrls(String tipoCamara, String tipoRover, String numeroDeSol) {
        try {
            String apiUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/" + tipoRover + "/photos?camera=" + tipoCamara + "&sol=" + numeroDeSol + "&api_key=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray photos = jsonResponse.getJSONArray("photos");

            // Usar ExecutorService para manejar hilos
            ExecutorService executor = Executors.newFixedThreadPool(10);

            for (int i = 0; i < photos.length(); i++) {
                JSONObject photo = photos.getJSONObject(i);
                String imgSrc = photo.getString("img_src");

                // Usar hilos para procesar cada imagen
                executor.submit(() -> {
                    try {
                        images.add(new Images(imgSrc));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            executor.shutdown();

            // Esperar a que todas las tareas se completen antes de continuar
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Images> getImages() {
        return images;
    }

    public int obtenerFilas() {
        return (int) Math.ceil(images.size() / 3.0);
    }
}
