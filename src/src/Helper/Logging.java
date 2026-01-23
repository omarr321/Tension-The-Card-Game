package Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import static Helper.Utility.charPrintWithIntEx;

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
                processMessage(message);
            } catch (InterruptedException e) {
                System.err.println("Logging Thread interrupted... cleaning up!");
                break;
            }
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
        System.err.println("Logging | Cleaning up logs...");
        System.err.println("Logging | Working thought the last of the log files");
        while (!messages.isEmpty()) {
            MessageWrapper message = messages.pollFirst();
            if (message != null) {
                try {
                    processMessage(message);
                } catch (InterruptedException e) {}
            }
        }

        System.err.println("Logging | closing BufferWriters...");
        try {
            if (cmdWriter != null) cmdWriter.close();
            if (gameActionsWriter != null) gameActionsWriter.close();
            if (alertsWriter != null) alertsWriter.close();
            if (logsWriter != null) logsWriter.close();
        } catch (IOException e) {
            System.err.println("Logging | Error closing log writers: " + e.getMessage());
        }

        System.err.println("Logging | Creating Master Log File...");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
        String formattedDT = Instant.now().atZone(ZoneId.systemDefault()).format(formatter);

        Path gameLogsDir = Paths.get(new File("").getAbsolutePath(), "game_logs");
        if (!Files.exists(gameLogsDir)) {
            try {
                Files.createDirectories(gameLogsDir);
            } catch (IOException e) {
                System.err.println("Logging | Failed to create Master Log file: " + e.getMessage());
            }
        }

        System.err.println("Logging | Getting and Sorting individual logs...");
        Path masterLog = Paths.get(new File("").getAbsolutePath(), "game_logs", formattedDT + "_game.log");
        Path[] individualLogs = {cmd, gameActions, alerts, logs};
        List<String> allLines = new ArrayList<>();

        for (Path path : individualLogs) {
            if (path != null && Files.exists(path)) {
                try {
                    allLines.addAll(Files.readAllLines(path));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        allLines.sort((a, b) -> {
            String tsA = extractTimestamp(a);
            String tsB = extractTimestamp(b);

            return tsA.compareTo(tsB);
        });

        System.err.println("Logging | Writing Master Log...");
        try {
            Files.write(masterLog, allLines);
            System.err.println("Logging | Master log successfully written!");
        } catch (IOException e) {
            System.err.println("Logging | Failed to write master log: " + e.getMessage());
        }

        System.err.println("Logging | Deleting individual log files...");
        for (Path path : individualLogs) {
            boolean deleted = false;
            try {
                deleted = Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Logging | Could not delete " + path.getFileName() + ": " + e.getMessage());
            }
            if (deleted) {
                System.err.println("Logging | Deleted temporary file: " + path.getFileName());
            }
        }
        System.err.println("Logging | All Log files deleted!");
    }

    private static String extractTimestamp(String line) {
        String currentTime = line.split("\\[")[1].split("]")[0];
        String[] curTimeSplited = currentTime.split(" ");
        String[] dateSplited = curTimeSplited[0].split("-");
        return dateSplited[2] + dateSplited[1] + dateSplited[0] + " " + curTimeSplited[1];
    }

    private static void processMessage(MessageWrapper message) throws InterruptedException {
        if (message == null) return;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
            String formattedDT = Instant.now().atZone(ZoneId.systemDefault()).format(formatter);

            switch (message.getType()) {
                case MESSAGE:
                    charPrintWithIntEx(message.getMessage(), 10);
                    logsWriter.append("MESSAGE [").append(formattedDT).append("]: ").append(message.getMessage()).append("\n");
                    logsWriter.flush();
                    break;
                case ALERT:
                    alertsWriter.append("ALERT [").append(formattedDT).append("]: ").append(message.getMessage()).append("\n");
                    alertsWriter.flush();
                    break;
                case CMD:
                    cmdWriter.append("CMD [").append(formattedDT).append("]: ").append(message.getMessage()).append("\n");
                    cmdWriter.flush();
                    break;
                case ADM_CMD:
                    cmdWriter.append("ADM_CMD [").append(formattedDT).append("]: ").append(message.getMessage()).append("\n");
                    cmdWriter.flush();
                    break;
                case GAME_STATES:
                    alertsWriter.append("GAME_STATES [").append(formattedDT).append("]: ").append(message.getMessage()).append("\n");
                    alertsWriter.flush();
                    break;
                case GAME_ACTION:
                    gameActionsWriter.append("GAME_ACTION [").append(formattedDT).append("]: ").append(message.getMessage()).append("\n");
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
