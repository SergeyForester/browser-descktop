package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;
import utils.HttpTools;

import java.io.IOException;

public class WeatherController {

    public TextField weatherTextField;
    public Label cityName;
    public Label weatherData;
    public Button weatherSubmitBtn;

    private String api_key = "8889b3e531767782401ad5b18be7bdd6";
    private String api_base_url = "http://api.openweathermap.org/data/2.5/weather?q=";

    @FXML
    private void initialize() {
        HttpTools httpTools = new HttpTools();

        weatherSubmitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // get city name
                String city = weatherTextField.getText();

                // fetching data
                try {
                    System.out.println(api_base_url + city + "&appid=" + api_key + "&units=metric");
                    JSONObject data = httpTools.get(api_base_url + city + "&appid=" + api_key + "&units=metric");

                    System.out.println(data);

                    cityName.setText(data.getString("name"));

                    JSONObject weather = (JSONObject) data.getJSONArray("weather").get(0);

                    weatherData.setText(weather.getString("main") + ", " + data.getJSONObject("main").getFloat("temp") + "°C");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
