package Helper;

public class Utility {
    public static int getRandInt (int min, int max) {
        int diff = max-min;
        return (int)(Math.random() * diff + min);
    }

    public static void charPrint(String message) {charPrint(message, 50);}
    public static void charPrint(String message, int delay) {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            sleep(delay);
        }
        System.out.println();
    }

    public static void charPrintWithIntEx(String message, int delay) throws InterruptedException {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            sleepWithIntEx(delay);
        }
        System.out.println();
    }

    public static void sleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException _) {}
    }

    public static void sleepWithIntEx(int milli) throws InterruptedException {
            Thread.sleep(milli);
    }
}
