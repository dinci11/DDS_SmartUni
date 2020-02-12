import gen.Humidity;
import gen.Smoke;
import gen.Temperature;
import subscribers.*;

import java.util.Date;

public class SubscriberMain {

    public static void main(String[] args) {
        CoolingSubscriber coolingSubscriber = new CoolingSubscriber(0, "Cooling", new CoolingSubscriber.ICooler() {
            @Override
            public void cooling(double intensity) {
                System.out.println("From Cooling handler!!!!!!!!!!!!!");
            }
        });
        HeatingSubscriber heatingSubscriber = new HeatingSubscriber(0, "Heating", new HeatingSubscriber.IHeater() {
            @Override
            public void heating(double intensity) {
                System.out.println("From HeatingSubscriber handler!!!!!!!!!!!!!");
            }
        });
        HumiditySubscriber humiditySubscriber = new HumiditySubscriber(0, "Humidity", new HumiditySubscriber.IHumidityDataAvailable() {
            @Override
            public void onHumidityDataAvailable(Humidity h) {
                System.out.println("From HumiditySubscriber handler!!!!!!!!!!!!!");
            }
        });
        SmokeSubscriber smokeSubscriber = new SmokeSubscriber(0, "Smoke", new SmokeSubscriber.ISmokeDataAvailable() {
            @Override
            public void onSmokeDataAvailable(Smoke s) {
                System.out.println("From SmokeSubscriber handler!!!!!!!!!!!!!");
            }
        });
        TemperatureSubscriber temperatureSubscriber = new TemperatureSubscriber(0, "Temperature", new TemperatureSubscriber.ITemperatureDataAvailable() {
            @Override
            public void onTemperatureDataAvailable(Temperature t) {
                System.out.println("From TemperatureSubscriber handler!!!!!!!!!!!!!");
            }
        });
        WindowOpenSubscriber windowOpenSubscriber = new WindowOpenSubscriber(0, "WindowOpen", new WindowOpenSubscriber.IWindowOpener() {
            @Override
            public void openWindow(double intensity) {
                System.out.println("From WindowOpenSubscriber handler!!!!!!!!!!!!!");
            }
        });
        try {
            while (true) {
                System.out.println("Waiting...");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            coolingSubscriber.ShutDownSubscriber();
            heatingSubscriber.ShutDownSubscriber();
            humiditySubscriber.ShutDownSubscriber();
            smokeSubscriber.ShutDownSubscriber();
            temperatureSubscriber.ShutDownSubscriber();
            windowOpenSubscriber.ShutDownSubscriber();
        }
    }

}
