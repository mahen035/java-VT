package com.training;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
public class ProcessWatcher {
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
            WatchService watchService = FileSystems.getDefault().newWatchService();
            dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            System.out.println("[Process B] Watching for changes to: " + file.toAbsolutePath());
            while (true) {
                WatchKey key = watchService.take(); // Blocking wait
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path changed = dir.resolve((Path) event.context());
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY && changed.equals(file)) {
                        String content = Files.readString(file, StandardCharsets.UTF_8);
                        System.out.println("[Process B] File updated:\n" + content);
                    }
                }

                key.reset();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

