package controller;

import api.CalendarAPI;
import api.WeatherAPI;
import gen.*;
import logger.Logger;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import publishers.CoolingPublisher;
import publishers.HeatingPublisher;
import publishers.WindowOpenPublisher;

import java.io.IOException;

public class ControllerLogic implements Runnable {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    private double currentCooling;
    private double currentHeating;
    private double currentWindowOpen;

    private int panelTemperature = 20;
    private int panelHumidity = 50;

    private boolean isRunning;
    private WindowOpenPublisher windowOpenPublisher;
    private CoolingPublisher coolingPublisher;
    private HeatingPublisher heatingPublisher;
    private CircularFifoQueue<Temperature> temperatureQueue;
    private CircularFifoQueue<Humidity> humidityQueue;
    private CircularFifoQueue<Smoke> smokeQueue;
    private Thread thread;
    private WeatherData currentWeather = null;


    public ControllerLogic(CircularFifoQueue<Temperature> temperatureQueue, CircularFifoQueue<Humidity> humidityQueue, CircularFifoQueue<Smoke> smokeQueue) {
        windowOpenPublisher = new WindowOpenPublisher(0, "WindowOpenTopic");
        coolingPublisher = new CoolingPublisher(0, "CoolingTopic");
        heatingPublisher = new HeatingPublisher(0, "HeatingTopic");
        isRunning = true;
        this.temperatureQueue = temperatureQueue;
        this.humidityQueue = humidityQueue;
        this.smokeQueue = smokeQueue;
        thread = new Thread(this);
        thread.start();
        currentWindowOpen = 0;
        currentCooling = 0;
        currentWindowOpen = 0;
        try {
            currentWeather = WeatherAPI.GetCurrentWeatherData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            while (isRunning) {
                if (!temperatureQueue.isEmpty())
                    CheckTemperature();
                if (!humidityQueue.isEmpty())
                    CheckHumidity();
                if (!smokeQueue.isEmpty())
                    CheckSmoke();

                Thread.sleep(4 * SECOND);
                try {
                    currentWeather = WeatherAPI.GetCurrentWeatherData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void CheckSmoke() {
        Smoke s = smokeQueue.peek();
        if (s.value > 40 && currentWindowOpen < 10) {
            openWindow(10);
        }
        if (s.value > 40 && currentWindowOpen < 40) {
            openWindow(40);
            Alert();
        }
    }

    private void Alert() {
        Logger.logError("Smoke value is too high!!!!");
    }

    private void CheckHumidity() throws InterruptedException {
        Humidity hum = humidityQueue.peek();
        CalendarData date = CalendarAPI.GetCalendarData();
        if (!date.isLecture) {
            if (hum.value > 60 || hum.value < 30) {
                if (currentWeather.windSpeed > 30 || currentWindowOpen < 30) {
                    openWindow(30);
                } else {
                    openWindow(70);
                }
                Thread.sleep(5 * SECOND);
            }

        } else {
            if (hum.value > 55 || hum.value < 45) {
                if (currentWeather.windSpeed > 30 || currentWindowOpen < 20) {
                    openWindow(20);
                } else {
                    openWindow(50);
                }
                Thread.sleep(3 * SECOND);
            }
        }
    }

    private void CheckTemperature() {
        Temperature temp = temperatureQueue.peek();
        CalendarData date = CalendarAPI.GetCalendarData();

        if (!date.isLecture) {
            if (temp.value > 26) {
                if (currentWeather != null && currentWeather.outTemperature + 5 < 26) {
                    openWindow(50);
                    cooling(20);
                } else {
                    cooling(30);
                }
            } else if (temp.value < 14) {
                if (currentWeather.outTemperature < 16) {
                    openWindow(0);
                }
                heating(30);
            } else {
                heating(0);
                cooling(0);
            }
        } else {
            if (temp.value > 22) {
                cooling(50);
            } else if (temp.value < 16) {
                heating(50);
            } else if (temp.value < panelTemperature) {
                heating(20);
            } else if (temp.value > panelTemperature) {
                cooling(20);
            } else {
                heating(0);
                cooling(0);
            }
        }
    }

    private void heating(double intensity) {
        heatingPublisher.WriteHeatingData(intensity);
        currentHeating = intensity;
    }

    private void cooling(double intensity) {
        coolingPublisher.WriteCoolingData(intensity);
        currentCooling = intensity;
    }

    private void openWindow(double intensity) {
        windowOpenPublisher.WriteWindowOpenData(intensity);
        currentWindowOpen = intensity;
    }


    public void ShutDown() {
        isRunning = false;
        Logger.logSimpleString("WindowOpenPublisher shutting down...");
        windowOpenPublisher.ShutDownPublisher();
        Logger.Done();
        Logger.logSimpleString("CoolingPublisher shutting down...");
        coolingPublisher.ShutDownPublisher();
        Logger.Done();
        Logger.logSimpleString("HeatingPublisher shutting down...");
        heatingPublisher.ShutDownPublisher();
        Logger.Done();
    }

    public void panelValuesChanged(int temperature, int humidity) {
        panelTemperature = temperature;
        panelHumidity = humidity;
    }
}
