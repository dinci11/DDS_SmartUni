package actuators;

import subscribers.CoolingSubscriber;

public class CoolerMain {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    public static void main(String[] args) {
        System.out.println("Cooler starting...");
        Cooler cooler = new Cooler();
        CoolingSubscriber coolingSubscriber = new CoolingSubscriber(0, "CoolingTopic", cooler);
        try {
            System.out.println("Cooler started");
            while (true) {
                Thread.sleep(3 * SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            coolingSubscriber.ShutDownSubscriber();
        }
    }

    public static class Cooler implements CoolingSubscriber.ICooler {

        public Cooler() {
        }

        @Override
        public void cooling(double intensity) {
            System.out.println("Set cooler to " + intensity);
        }
    }
}
