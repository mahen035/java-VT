package com.training;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
public class ProcessWriter {
    public static void main(String[] args) {
        Path dir = Paths.get("shared");
        Path file = dir.resolve("data.txt");
        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
                System.out.println("Directory created: " + dir.toAbsolutePath());
            }
            if (!Files.exists(file)) {
                Files.createFile(file);
                System.out.println("File created: " + file.toAbsolutePath());
            }
            int count = 0;
            while (true) {
                try (FileChannel channel = FileChannel.open(file,
                        StandardOpenOption.WRITE
                        )) {

                    String message = "Update #" + (++count) + " at " + System.currentTimeMillis() + "\n";
                    ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                    channel.write(buffer);
                    channel.force(true); // Ensure data is flushed to disk
                    System.out.println("[Process A] Wrote: " + message.trim());
                }

                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//StandardOpenOption.TRUNCATE_EXISTING
//,StandardOpenOption.APPEND
