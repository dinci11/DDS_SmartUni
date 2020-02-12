package sensors;

import publishers.HumidityPublisher;

import java.util.Date;
import java.util.Random;

public class HumiditySensorMain {
    private static HumidityPublisher humidityPublisher;
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static Random random;

    private static int time = 0;

    public static void main(String[] args) {
        humidityPublisher = new HumidityPublisher(0, "HumidityTopic");
        random = new Random();

        try {
            while (true) {
                SendHumidityData();
                time=(time+1)%200;
                Thread.sleep(3 * SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            humidityPublisher.ShutDownPublisher();
        }
    }

    private static void SendHumidityData() {

        //y = 47.78166 - 2.148666*x + 0.05781041*x^2 - 0.0004613105*x^3 + 0.00000112634*x^4
        double x1 = time;
        double x2 = time*time;
        double x3 = time*time*time;
        double x4 = time*time*time*time;

        double hum = 47.78166 - (2.148666 * x1) + (0.05781041 * x2) - (0.0004613105 * x3) + (0.00000112634 * x4);

        humidityPublisher.WriteHumidityData((int) Math.round(hum), new Date());
    }
}
