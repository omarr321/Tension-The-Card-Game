package Engine;

import Helper.Logging;
import Helper.MessageWrapper;
import Helper.Utility;
import Helper.Watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameController {
    private static Watcher watcher;
    private static final ArrayList<Thread> threads = new ArrayList<>();
    private static final ArrayList<Process> processes = new ArrayList<>();
    public static void main(String[] args) {
        Logging.setupLogFiles();

        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating Logging Thread..."));
        Thread logThread = new Thread(new Logging());
        logThread.setName("System-Logger");
        logThread.start();
        threads.add(logThread);

        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating the log files..."));
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating processes that open and read all the log files..."));

        //ProcessBuilder readLogLogs = new ProcessBuilder("cmd", "/c", "start", "powershell", "-NoExit", "Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "logs.log").toString() + "' -Wait");
        ProcessBuilder readLogLogs = new ProcessBuilder(
                "powershell.exe",
                "-NoExit",
                "-ExecutionPolicy", "Bypass",
                "-Command", "Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "logs.log") + "' -Wait"
        );

        readLogLogs.redirectErrorStream(true);
        //ProcessBuilder readLogCmd = new ProcessBuilder("cmd", "/c", "start", "powershell", "-NoExit", "Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "cmd.log").toString() + "' -Wait");
        //TODO: Add other logging here!


        try {
            processes.add(readLogLogs.start());
            //processes.add(readLogCmd.start());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        threads.add(Thread.currentThread());

        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating watcher thread..."));
        watcher = new Watcher(processes.toArray(new Process[0]), threads.toArray(new Thread[0]));
        Thread watcherThread = new Thread(watcher);
        watcherThread.setName("System-Watcher");
        watcherThread.start();

        Utility.sleep(1000);
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.CMD, "CMD TEST 1"));
        Utility.sleep(2500);
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Test 1"));
        Utility.sleep(1000);
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Test 2"));
        Utility.sleep(2000);
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.CMD, "CMD TEST 2"));
        Utility.sleep(1500);
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.CMD, "CMD TEST 3"));
        Logging.QueuePriorityLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Priority Log"));
        Utility.sleep(1000000);
    }
}
