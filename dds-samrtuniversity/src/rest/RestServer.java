package rest;

import phone_data.PhoneDataPublisher;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;

import static spark.Spark.*;

public class RestServer {
    public static void main(String[] args) {

        PhoneDataPublisher phoneDataPublisher = new PhoneDataPublisher(0,"PhoneDataTopic");

        get("/hello", (req, res) -> "Hello World");
        post("/phoneData",((request, response) -> {
            try {
                int deviceID = Integer.parseInt(request.queryParams("deviceID"));
                double temperature = Double.parseDouble(request.queryParams("temp"));
                double humidity = Double.parseDouble(request.queryParams("hum"));
                Date timeStamp = new Date(Long.parseLong(request.queryParams("date")));

                System.out.println(deviceID + ", " + temperature + ", " + humidity + ", " + timeStamp);
                phoneDataPublisher.WritePhoneData(deviceID,temperature,humidity,timeStamp);

                response.status(200);
                return "OK";
            }
            catch (Exception e){
                return "NOT OK";
            }
        }));
        System.out.println("Server running...");
    }
}
