package sensors;

import publishers.TemperaturePublisher;

import java.util.Date;
import java.util.Random;

public class TemperatureSensorMain {

    private static TemperaturePublisher temperaturePublisher;
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static Random random;

    private static int time = 0;

    public static void main(String[] args) {

        temperaturePublisher = new TemperaturePublisher(0, "TemperatureTopic");
        random = new Random();

        try {
            while (true) {
                SendTemperatureData();
                time = (time + 1) % 200;
                Thread.sleep(3*SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            temperaturePublisher.ShutDownPublisher();
        }
    }

    private static void SendTemperatureData() {
        //y = 1.62331 - 0.07858275*x + 0.002457622*x^2 - 0.00002064709*x^3 + 5.161772e-8*x^4
        double x1 = time;
        double x2 = time*time;
        double x3 = time*time*time;
        double x4 = time*time*time*time;
        double e = 0.00000005161772;

        double magic = 1.62331 - (0.07858275 * x1) + (0.002457622 * x2) - (0.00002064709 * x3) + (e * x4);
        temperaturePublisher.WriteTemperatureData((int) Math.round(10*magic), new Date());
    }

}
