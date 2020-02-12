package logger;

import gen.Humidity;
import gen.Smoke;
import gen.Temperature;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.InfluxDBException.DatabaseNotFoundException;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import phone_data.PhoneData;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Logger {

    private static InfluxDB influxDB;
    private static final String dbURL = "http://localhost:8086";
    private static final String dbUsername = "app";
    private static final String dbPassword = "app";
    private static final String dbName = "smartuniversity";
    private static final String measurementName = "smartuni";
    private static final String phoneDataMeasurementName = "crowd";
    private static boolean isDBAlive = false;

    public static void Init() throws InfluxDBException {

        influxDB = InfluxDBFactory.connect(dbURL,dbUsername,dbPassword);
        Pong response = influxDB.ping();
        if(response.getVersion().equalsIgnoreCase("unknown")){
        throw new InfluxDBException("Database NOT found!");
        }
        isDBAlive = true;
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        influxDB.setDatabase(dbName);
        influxDB.enableBatch(BatchOptions.DEFAULTS);

    }

    public static void logBtnSetClicked(int temperature, int humidity) {
        System.out.println("Set request arrived: temperature:" + temperature + "celsius - humidity:" + humidity + "%");
    }

    public static void logDataAvailable(Humidity humidity) {
        System.out.println("Humidity data available: " + humidity.value + " - " + new Date(humidity.timeStamp));
        addToDB(humidity);
    }

    public static void logDataAvailable(Smoke smoke) {
        System.out.println("Smoke data available: " + smoke.value + " - " + new Date(smoke.timeStamp));
        addToDB(smoke);
    }

    public static void logDataAvailable(Temperature temperature) {
        System.out.println("Temperature data available: " + temperature.value + " - " + new Date(temperature.timeStamp));
        addToDB(temperature);
    }

    public static void logSimpleString(String data) {
        System.out.println(data);
    }

    public static void logError(String data){
        System.err.println(data);
    }

    public static void addToDB(Temperature data){
        influxDB.write(Point.measurement(measurementName)
                .time(data.timeStamp, TimeUnit.MILLISECONDS)
                .tag("unit", "temperature")
                .addField("value", data.value)
                .build());
    }
    public static void addToDB(Humidity data){
        influxDB.write(Point.measurement(measurementName)
                .time(data.timeStamp, TimeUnit.MILLISECONDS)
                .tag("unit", "humidity")
                .addField("value", data.value)
                .build());
    }
    public static void addToDB(Smoke data){
        influxDB.write(Point.measurement(measurementName)
                .time(data.timeStamp, TimeUnit.MILLISECONDS)
                .tag("unit", "smoke")
                .addField("value", data.value)
                .build());
    }

    public static void Done() {
        System.out.println("====DONE====");
    }

    public static void Done(String function) {
        System.out.println("===="+function+": DONE====");
    }

    public static void logDataAvailable(PhoneData phoneData) {
        addToDB(phoneData);
        System.out.println("Phone Data Available: " + phoneData.deviceID + " - " + phoneData.temperature + " - " + phoneData.humidity + " - " + phoneData.timeStamp);
    }

    public static void addToDB(PhoneData phoneData) {
        influxDB.write(Point.measurement(phoneDataMeasurementName)
                .time(phoneData.timeStamp, TimeUnit.MILLISECONDS)
                .tag("device", phoneData.deviceID+"")
                .addField("temp",phoneData.temperature)
                .addField("hum", phoneData.humidity)
                .build());
    }

    public static void Close(){
        influxDB.close();
    }
}
