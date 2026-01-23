package Helper;

public class Watcher implements Runnable{
    private Process[] processes;
    private Thread[] threads;

    public Watcher(Process[] processes, Thread[] threads) {
        this.processes = processes;
        this.threads = threads;
    }

    @Override
    public void run() {
        System.out.println("Watcher | Monitoring health...");

        while (true) {
            if (!checkHealth()) {
                System.err.println("Watcher | System failure detected. Initiating shutdown...");
                this.killAll();
                break;
            }
            Utility.sleep(1000);
        }

        System.err.println("Watcher | Waiting for all threads to die...");
        while(!(allKilled())) {
            Utility.sleep(1000);
        }
        System.err.println("Watcher | Done!");
    }

    private boolean allKilled() {
        for (Process p : processes) {
            if (p != null && p.isAlive()) return false;
        }

        // Check all managed threads (including the Logging thread)
        for (Thread t : threads) {
            if (t != null && t.isAlive()) return false;
        }

        return true;
    }

    private void killAll() {
        for (Process p : processes) {
            if (p.isAlive()) {
                p.descendants().forEach(ProcessHandle::destroyForcibly);
                p.destroyForcibly();
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