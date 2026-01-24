package Engine;

import Helper.Logging;
import Helper.MessageWrapper;
import Helper.Utility;
import Helper.Watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class GameController {
    private static Watcher watcher;
    private static final ArrayList<Thread> threads = new ArrayList<>();
    private static final ArrayList<Process> processes = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Logging.setupLogFiles();

        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating Logging Thread..."));
        Thread logThread = new Thread(new Logging());
        logThread.setName("System-Logger");
        logThread.start();
        threads.add(logThread);

        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating the log files..."));
        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating processes that open and read all the log files..."));

        final List<String> commands = new ArrayList<String>();
        commands.add("cmd.exe");
        commands.add("/C");
        commands.add("start");
        commands.add("/wait");
        commands.add("powershell");
        commands.add("-NoExit");

        final List<String> cmdCommands = new ArrayList<>(commands);
        cmdCommands.add("$Host.UI.RawUI.WindowTitle = 'Command Log File';");
        cmdCommands.add("Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "cmd.log").toString() + "' -Wait");
        ProcessBuilder logCMD = new ProcessBuilder(cmdCommands);

        final List<String> logCommands = new ArrayList<>(commands);
        logCommands.add("$Host.UI.RawUI.WindowTitle = 'General Log File';");
        logCommands.add("Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "logs.log").toString() + "' -Wait");
        ProcessBuilder logLog = new ProcessBuilder(logCommands);

        final List<String> alertsCommands = new ArrayList<>(commands);
        alertsCommands.add("$Host.UI.RawUI.WindowTitle = 'Alerts Log File';");
        alertsCommands.add("Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "alerts.log").toString() + "' -Wait");
        ProcessBuilder logAlerts = new ProcessBuilder(alertsCommands);

        final List<String> gameActionsCommands = new ArrayList<>(commands);
        gameActionsCommands.add("$Host.UI.RawUI.WindowTitle = 'Game History Log File';");
        gameActionsCommands.add("Get-Content '" + Paths.get(new File("").getAbsolutePath(), "logs", "gameActions.log").toString() + "' -Wait");
        ProcessBuilder logGameActions = new ProcessBuilder(gameActionsCommands);

        try {
            processes.add(logCMD.start());
            processes.add(logLog.start());
            processes.add(logAlerts.start());
            processes.add(logGameActions.start());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        threads.add(Thread.currentThread());

        Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Creating watcher thread..."));
        watcher = new Watcher(processes.toArray(new Process[0]), threads.toArray(new Thread[0]));
        Thread watcherThread = new Thread(watcher);
        watcherThread.setName("System-Watcher");
        watcherThread.start();

        try {
            Utility.sleepWithIntEx(1000);
            Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.CMD, "CMD TEST 1"));
            Utility.sleepWithIntEx(2500);
            Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Test 1"));
            Utility.sleepWithIntEx(1000);
            Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Test 2"));
            Utility.sleepWithIntEx(2000);
            Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.CMD, "CMD TEST 2"));
            Utility.sleepWithIntEx(1500);
            Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.CMD, "CMD TEST 3"));
            Logging.QueuePriorityLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Priority Log"));

            for (int i = 0; i < 100; i++) {
                Logging.QueueLog(new MessageWrapper(Logging.LOG_TYPE.MESSAGE, "Test loop - " + i));
            }

            Utility.sleepWithIntEx(1000000);
        } catch (InterruptedException e) {
            System.err.println("Game Controller Thread interrupted... cleaning up!");
            System.err.println("Game Controller Thread Terminated!");
        }
    }
}
