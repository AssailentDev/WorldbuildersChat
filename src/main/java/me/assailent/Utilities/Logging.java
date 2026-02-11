package me.assailent.Utilities;

import me.assailent.WorldbuildersChat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {

    private static Path globalFile = null;
    private static Path localFile = null;
    private static Path swearFile = null;

    private Path dataFolder;

    public Logging(WorldbuildersChat plugin) {
        dataFolder = plugin.getDataDirectory();
        try {
            Files.createDirectories(dataFolder);

            globalFile = dataFolder.resolve("global-log.txt");
            localFile = dataFolder.resolve("local-log.txt");
            swearFile = dataFolder.resolve("swear-log.txt");

            if (!Files.exists(globalFile)) {
                Files.createFile(globalFile);
            }

            if (!Files.exists(localFile)) {
                Files.createFile(localFile);
            }

            if (!Files.exists(swearFile)) {
                Files.createFile(swearFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void log(String channel, String playerName, String message) {
        String timestamp = LocalDateTime.now().format(FORMAT);
        String line = String.format("[%s] %s: %s", timestamp, playerName, message);

        Path file = null;
        if (channel == "Global") {
            file = globalFile;
        } else if (channel == "Local") {
            file = localFile;
        } else if (channel == "Swear") {
            file = swearFile;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                file,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.write(line);
            writer.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
