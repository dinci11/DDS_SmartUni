package actuators;

import subscribers.WindowOpenSubscriber;

public class WindowOpenerMain {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;

    public static void main(String[] args) {
        System.out.println("WindowOpener starting...");
        WindowOpener windowOpener = new WindowOpener();
        WindowOpenSubscriber windowOpenSubscriber = new WindowOpenSubscriber(0, "WindowOpenTopic", windowOpener);

        try {
            System.out.println("WindowOpener started");
            while (true) {
                Thread.sleep(3 * SECOND);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            windowOpenSubscriber.ShutDownSubscriber();
        }
    }

    public static class WindowOpener implements WindowOpenSubscriber.IWindowOpener {
        public WindowOpener() {
        }

        @Override
        public void openWindow(double intensity) {
            System.out.println("Open window to " + intensity);
        }
    }
}
