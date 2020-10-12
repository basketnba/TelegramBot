package ru.geekbrains;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class Weather {

    //token 25630990bff5831d57aa511aa05f4db2
    static String getWeather(String message, Model model) throws IOException {
        String spec = "https://api.openweathermap.org/data/2.5/weather?q=" + message +
                "&units=metric&appid=25630990bff5831d57aa511aa05f4db2";
        URL url = new URL(spec);

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";
        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }

        JSONObject jsonObject = new JSONObject(result);
        model.setName(jsonObject.getString("name"));

        JSONObject main = jsonObject.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray weather = jsonObject.getJSONArray("weather");
        for (int i = 0; i < weather.length(); i++) {
            JSONObject object = weather.getJSONObject(i);
            model.setIcon(object.getString("icon"));
            model.setMain(object.getString("main"));
        }

        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Main: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/wn/" + model.getIcon() + "@2x.png" + "\n";
    }
}