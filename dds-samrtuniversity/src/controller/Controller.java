package controller;

import gen.*;
import logger.Logger;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import phone_data.PhoneData;
import phone_data.PhoneDataSubscriber;
import subscribers.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller implements ControllerFrame.ButtonSet_OnClicked, TemperatureSubscriber.ITemperatureDataAvailable, HumiditySubscriber.IHumidityDataAvailable, SmokeSubscriber.ISmokeDataAvailable, PhoneDataSubscriber.IPhoneDataDataAvailable {

    ControllerFrame frame;
    ControllerLogic controllerLogic;
    Thread controllerLogicThread;
    CircularFifoQueue<Temperature> temperatureQueue;
    CircularFifoQueue<Humidity> humidityQueue;
    CircularFifoQueue<Smoke> smokeQueue;


    public Controller(ControllerFrame frame) {
        this.frame = frame;
        frame.setListener(this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frame.setEnabled(false);
                ShutDown();
            }
        });
        temperatureQueue = new CircularFifoQueue<>(100);
        humidityQueue = new CircularFifoQueue<>(100);
        smokeQueue = new CircularFifoQueue<>(100);

        controllerLogic = new ControllerLogic(temperatureQueue,humidityQueue,smokeQueue);
        controllerLogicThread = new Thread(controllerLogic);
        controllerLogicThread.start();
    }

    @Override
    public void btnSetOnClicked(int temperature, int humidity) {
        Logger.logBtnSetClicked(temperature, humidity);
        controllerLogic.panelValuesChanged(temperature,humidity);
    }

    @Override
    public void onHumidityDataAvailable(Humidity humidity) {
        humidityQueue.add(humidity);
        frame.SetCurrentHumidity(humidity.value);
        Logger.logDataAvailable(humidity);
    }

    @Override
    public void onSmokeDataAvailable(Smoke smoke) {
        smokeQueue.add(smoke);
        Logger.logDataAvailable(smoke);
    }

    @Override
    public void onTemperatureDataAvailable(Temperature temperature) {
        temperatureQueue.add(temperature);
        frame.SetCurrentTemperature(temperature.value);
        Logger.logDataAvailable(temperature);

    }

    public void ShutDown(){
        controllerLogic.ShutDown();
    }

    @Override
    public void onPhoneDataDataAvailable(PhoneData phoneData) {
        Logger.logDataAvailable(phoneData);
    }
}
