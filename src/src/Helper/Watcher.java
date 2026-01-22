package Helper;

//TODO: Make a watcher thead that watchs all process of the game and close down eveyrthting if a process crashes + cleanup
public class Watcher implements Runnable{
    private Process[] processes;
    private Thread[] threads;

    public Watcher(Process[] processes, Thread[] threads) {
        this.processes = processes;
        this.threads = threads;
    }

    @Override
    public void run() {
        System.out.println("Checking health of all process and threads...");
        while (true) {
            if (!checkHealth()) {
                System.out.println("A process or thread died...");
                Logging.cleanupLogFiles();
                this.killAll();
                break;
            }
            System.out.println("All processes and threads are good...");
            Utility.sleep(1000);
        }
    }

    private void killAll() {
        for (Process p : processes) {
            if (p.isAlive()) {
                p.destroy();
            }
        }

        for (Thread t : threads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
    }

    private boolean checkHealth() {
        for (Process p : processes) {
            if (!p.isAlive()) {
                return false;
            }
        }

        for (Thread t : threads) {
            if (!t.isAlive()) {
                return false;
            }
        }

        return true;
    }
}
