package sensors;

import publishers.SmokePublisher;

import java.util.Date;
import java.util.Random;

public class SmokeSensorMain {
    private static SmokePublisher smokePublisher;
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static Random random;

    private static int time = 0;

    public static void main(String[] args) {

        smokePublisher = new SmokePublisher(0, "SmokeTopic");
        random = new Random();

        try {
            while (true) {
                SendSmokeData();
                time = (time + 1) % 200;
                Thread.sleep(3 * SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            smokePublisher.ShutDownPublisher();
        }
    }

    private static void SendSmokeData() {
        int min = 0;
        int max = 10;
        if (time < 75) {
            min = 0;
            max = 10;
        } else if (time < 100) {
            min = 20;
            max = 30;
        } else if (time < 125) {
            min = 40;
            max = 60;
        } else if (time < 150) {
            min = 20;
            max = 30;
        }

        int smoke = min + new Random().nextInt(max - min + 1);
        smokePublisher.WriteSmokeData(smoke, new Date());
    }
}
