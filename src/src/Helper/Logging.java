package Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;

import static Helper.Utility.charPrint;

//TODO: Set up the logging class to accept messages from other threads and metheds and log them to the console/file
public class Logging {
    private static Path cmd, gameActions, alerts, logs;
    private static BufferedWriter cmdWriter, gameActionsWriter, alertsWriter, logsWriter;

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
        //TODO: Clean up log files
    }

    public static void QueueLog(LOG_TYPE type, String message) {
        try {
            switch (type) {
                case MESSAGE:
                    charPrint(message);
                    logsWriter.append("MESSAGE [").append(String.valueOf(Instant.now())).append("]: ").append(message).append("\n");
                    logsWriter.flush();
                    break;
                case ALERT:
                    alertsWriter.append("ALERT [").append(String.valueOf(Instant.now())).append("]: ").append(message).append("\n");
                    alertsWriter.flush();
                    break;
                case CMD:
                    cmdWriter.append("CMD [").append(String.valueOf(Instant.now())).append("]: ").append(message).append("\n");
                    cmdWriter.flush();
                    break;
                case ADM_CMD:
                    cmdWriter.append("ADM_CMD [").append(String.valueOf(Instant.now())).append("]: ").append(message).append("\n");
                    cmdWriter.flush();
                    break;
                case GAME_STATES:
                    alertsWriter.append("GAME_STATES [").append(String.valueOf(Instant.now())).append("]: ").append(message).append("\n");
                    alertsWriter.flush();
                    break;
                case GAME_ACTION:
                    gameActionsWriter.append("GAME_ACTION [").append(String.valueOf(Instant.now())).append("]: ").append(message).append("\n");
                    gameActionsWriter.flush();
                    break;
                default:
                    System.err.println("Error: No log type found");
            }
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }


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
