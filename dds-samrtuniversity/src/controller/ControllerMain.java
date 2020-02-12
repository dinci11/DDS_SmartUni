package controller;

import api.CalendarAPI;
import api.WeatherAPI;
import logger.Logger;
import org.influxdb.InfluxDBException;
import phone_data.PhoneDataSubscriber;
import subscribers.HumiditySubscriber;
import subscribers.SmokeSubscriber;
import subscribers.TemperatureSubscriber;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControllerMain {

    private static Controller controller;
    private static TemperatureSubscriber temperatureSubscriber;
    private static HumiditySubscriber humiditySubscriber;
    private static SmokeSubscriber smokeSubscriber;
    private static PhoneDataSubscriber phoneDataSubscriber;

    public static void main(String[] args) {
        try {
            Logger.Init();
        } catch (InfluxDBException e) {
            Logger.logError(e.getMessage());
        }
        WeatherAPI.Init();
        CalendarAPI.Init();
        ControllerFrame controllerFrame = new ControllerFrame("Controller");
        controller = new Controller(controllerFrame);

        CrowdSensing crowdSensing = new CrowdSensing();
        Thread crowdSensingThread = new Thread(crowdSensing);
        crowdSensingThread.start();

        temperatureSubscriber = new TemperatureSubscriber(0, "TemperatureTopic", controller);
        humiditySubscriber = new HumiditySubscriber(0, "HumidityTopic", controller);
        smokeSubscriber = new SmokeSubscriber(0, "SmokeTopic", controller);
        phoneDataSubscriber = new PhoneDataSubscriber(0,"PhoneDataTopic", controller);
        controllerFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                crowdSensing.Stop();
                controllerFrame.setEnabled(false);
                Logger.logSimpleString("Shuting down temperatureSubscriber");
                temperatureSubscriber.ShutDownSubscriber();
                Logger.Done();
                Logger.logSimpleString("Shuting down humiditySubscriber");
                humiditySubscriber.ShutDownSubscriber();
                Logger.Done();
                Logger.logSimpleString("Shuting down smokeSubscriber");
                smokeSubscriber.ShutDownSubscriber();
                Logger.Done();
                Logger.logSimpleString("Shuting down phoneDataSubscriber");
                phoneDataSubscriber.ShutDownSubscriber();
                Logger.Done();
                controllerFrame.dispose();
                Logger.Close();
            }
        });
    }
}
