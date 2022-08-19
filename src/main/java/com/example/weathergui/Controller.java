package com.example.weathergui;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text lbWeather;

    @FXML
    private Button button;

    @FXML
    private TextField textBox;

    @FXML
    private Text textInfo;

    @FXML
    void onHelloButtonClick(MouseEvent event) {
        lbWeather.setVisible(true);
        Application.city = textBox.getText();
        System.out.println(Application.city);

        String language = "ru"; //TODO: язык
        String weatherData = Logic.getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + Application.city + "&lang=" + language + "&appid=597fb0b809f7fca16fd9ce2c6dfade75&units=metric");
        JSONObject obj = new JSONObject(weatherData);
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("HH:mm:ss MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String firstSubstring = "name\":\"";
        String secondSubstring = "\",\"cod\"";
        textInfo.setVisible(true);
        String result = weatherData.substring(weatherData.indexOf(firstSubstring) + firstSubstring.length(), weatherData.indexOf(secondSubstring))
                + ", " +obj.getJSONObject("sys").getString("country") + "\n"; // Город, Страна
        result += dateTime.format(now) + "\n\n"; //Дата, время
        firstSubstring = "\"description\":\"";
        secondSubstring = "\",\"main\":\"";
        int firstSubstringIndex = obj.get("weather").toString().indexOf(firstSubstring) + firstSubstring.length();
        int secondSubstringIndex = obj.get("weather").toString().indexOf(secondSubstring);
        result += "Weather: " + obj.get("weather").toString().substring(firstSubstringIndex, secondSubstringIndex) + "\n";
        result += "Temperature: " + obj.getJSONObject("main").getDouble("temp")
                + " (Max: " + obj.getJSONObject("main").getDouble("temp_max")
                + " Min: " + obj.getJSONObject("main").getDouble("temp_min") + ")℃\n";
        result += "Feels like: " + obj.getJSONObject("main").getInt("feels_like") + "\n";
        result += "Pressure: " + obj.getJSONObject("main").getInt("pressure") + " hPa" + "\n";
        result += "Humidity: " + obj.getJSONObject("main").getInt("humidity") + "%" + "\n";
        result += "Wind: "  + obj.getJSONObject("wind").getInt("speed") + "m/s" + "\n";
        lbWeather.setText(result);
    }

    @FXML
    void initialize() {
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'view.fxml'.";
        assert textBox != null : "fx:id=\"textBox\" was not injected: check your FXML file 'view.fxml'.";
        assert textInfo != null : "fx:id=\"textInfo\" was not injected: check your FXML file 'view.fxml'.";
        assert lbWeather != null : "fx:id=\"lbWeather\" was not injected: check your FXML file 'view.fxml'.";
        textInfo.setVisible(false);
        textBox.setStyle("-fx-text-fill: black");
        lbWeather.setVisible(false);
    }

}
