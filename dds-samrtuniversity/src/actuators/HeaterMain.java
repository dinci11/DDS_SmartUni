package actuators;

import subscribers.HeatingSubscriber;

public class HeaterMain {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    public static void main(String[] args) {
        System.out.println("Heater starting...");
        Heater heater = new Heater();
        HeatingSubscriber heatingSubscriber = new HeatingSubscriber(0, "HeatingTopic", heater);
        try {
            System.out.println("Heater started");
            while (true) {
                Thread.sleep(3 * SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            heatingSubscriber.ShutDownSubscriber();
        }
    }

    public static class Heater implements HeatingSubscriber.IHeater {

        public Heater() {
        }

        @Override
        public void heating(double intensity) {
            System.out.println("Set heater to " + intensity);
        }
    }

}
