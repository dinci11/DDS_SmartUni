import publishers.*;

import java.util.Date;
import java.util.Random;

public class PublisherMain {

    public static void main(String[] args) {
        CoolingPublisher coolingPublisher = new CoolingPublisher(0, "Cooling");
        HeatingPublisher heatingPublisher = new HeatingPublisher(0, "Heating");
        HumidityPublisher humidityPublisher = new HumidityPublisher(0, "HumidityTopic");
        SmokePublisher smokePublisher = new SmokePublisher(0, "SmokeTopic");
        TemperaturePublisher temperaturePublisher = new TemperaturePublisher(0,"TemperatureTopic");
        WindowOpenPublisher windowOpenPublisher = new WindowOpenPublisher(0,"WindowOpen");

        try {
            while (true) {
                coolingPublisher.WriteCoolingData(new Random().nextDouble());
                heatingPublisher.WriteHeatingData(new Random().nextDouble());
                windowOpenPublisher.WriteWindowOpenData(new Random().nextDouble());

                humidityPublisher.WriteHumidityData(new Random().nextInt(100), new Date());
                smokePublisher.WriteSmokeData(new Random().nextInt(100), new Date());
                temperaturePublisher.WriteTemperatureData(new Random().nextInt(50), new Date());

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            coolingPublisher.ShutDownPublisher();
            heatingPublisher.ShutDownPublisher();
            humidityPublisher.ShutDownPublisher();
            smokePublisher.ShutDownPublisher();
            temperaturePublisher.ShutDownPublisher();
            windowOpenPublisher.ShutDownPublisher();
        }

    }
}
