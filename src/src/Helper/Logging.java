package Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.concurrent.LinkedBlockingDeque;

import static Helper.Utility.charPrint;

//TODO: Set up the logging class to accept messages from other threads and metheds and log them to the console/file
public class Logging implements Runnable{
    private static Path cmd, gameActions, alerts, logs;
    private static BufferedWriter cmdWriter, gameActionsWriter, alertsWriter, logsWriter;
    private static final LinkedBlockingDeque<MessageWrapper> messages = new LinkedBlockingDeque<>();

    @Override
    public void run() {
        while (true) {
            MessageWrapper message;
            try {
                message = messages.takeFirst();
            } catch (InterruptedException e) {
                System.err.println("Logging Thread interrupted... cleaning up!");
                break;
            }
            processMessage(message);
        }
        cleanupLogFiles();
        System.err.println("Logging Thread Terminated!");
    }

    public static void setupLogFiles() {
        String currentWorkingDirectory = new File("").getAbsolutePath();
        try {
            Files.createFile(Paths.get(currentWorkingDirectory, "logs", "cmd.log"));
            Files.createFile(Paths.get(currentWorkingDirectory, "logs", "gameActions.log"));
            Files.createFile(Paths.get(currentWorkingDirectory, "logs", "alerts.log"));
            Files.createFile(Paths.get(currentWorkingDirectory, "logs", "logs.log"));
        } catch (java.nio.file.FileAlreadyExistsException e) {
            System.err.println("File already exists: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
        cmd = Paths.get(currentWorkingDirectory, "logs", "cmd.log");
        gameActions = Paths.get(currentWorkingDirectory, "logs", "gameActions.log");
        alerts = Paths.get(currentWorkingDirectory, "logs", "alerts.log");
        logs = Paths.get(currentWorkingDirectory, "logs", "logs.log");
        try {
            cmdWriter = Files.newBufferedWriter(cmd);
            gameActionsWriter = Files.newBufferedWriter(gameActions);
            alertsWriter = Files.newBufferedWriter(alerts);
            logsWriter = Files.newBufferedWriter(logs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanupLogFiles() {
        while (!messages.isEmpty()) {
            MessageWrapper message = messages.pollFirst();
            if (message != null) {
                processMessage(message);
            }
        }

        try {
            if (cmdWriter != null) cmdWriter.close();
            if (gameActionsWriter != null) gameActionsWriter.close();
            if (alertsWriter != null) alertsWriter.close();
            if (logsWriter != null) logsWriter.close();
        } catch (IOException e) {
            System.err.println("Error closing log writers: " + e.getMessage());
        }
    }

    private static void processMessage(MessageWrapper message) {
        if (message == null) return;
        try {
            switch (message.getType()) {
                case MESSAGE:
                    charPrint(message.getMessage());
                    logsWriter.append("MESSAGE [").append(String.valueOf(Instant.now())).append("]: ").append(message.getMessage()).append("\n");
                    logsWriter.flush();
                    break;
                case ALERT:
                    alertsWriter.append("ALERT [").append(String.valueOf(Instant.now())).append("]: ").append(message.getMessage()).append("\n");
                    alertsWriter.flush();
                    break;
                case CMD:
                    cmdWriter.append("CMD [").append(String.valueOf(Instant.now())).append("]: ").append(message.getMessage()).append("\n");
                    cmdWriter.flush();
                    break;
                case ADM_CMD:
                    cmdWriter.append("ADM_CMD [").append(String.valueOf(Instant.now())).append("]: ").append(message.getMessage()).append("\n");
                    cmdWriter.flush();
                    break;
                case GAME_STATES:
                    alertsWriter.append("GAME_STATES [").append(String.valueOf(Instant.now())).append("]: ").append(message.getMessage()).append("\n");
                    alertsWriter.flush();
                    break;
                case GAME_ACTION:
                    gameActionsWriter.append("GAME_ACTION [").append(String.valueOf(Instant.now())).append("]: ").append(message.getMessage()).append("\n");
                    gameActionsWriter.flush();
                    break;
                default:
                    System.err.println("Error: No log type found");
            }
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

    public static void QueueLog(MessageWrapper message) {
        messages.addLast(message);
    }

    public static void QueuePriorityLog(MessageWrapper message) {
        messages.addFirst(message);
    }

    public enum LOG_TYPE {
        ALERT,
        MESSAGE,
        CMD,
        ADM_CMD,
        GAME_STATES,
        GAME_ACTION
    }
}
