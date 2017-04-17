package pl.tirt.dstcp.data.watcher;

import pl.tirt.dstcp.data.DataUtils;
import pl.tirt.dstcp.data.service.ProcessDataService;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Created by AWALICZE on 15.04.2017.
 */
public class DataChangeWatcher {

    private static Logger logger = Logger.getLogger(DataUtils.LOGGER_NAME);

    public static void watchFile(String directoryPath, String fileName) throws IOException {
        Path dirPath = new File(directoryPath).toPath();
        if(!isDirectory(dirPath)){
            throw new IllegalArgumentException("Invalid path: " + directoryPath);
        }

        WatchService watcher = FileSystems.getDefault().newWatchService();

        dirPath.register(watcher, ENTRY_MODIFY);
        logger.info("Watch Service registered for dir: " + directoryPath);

        startWatching(watcher,fileName);
    }

    private static boolean isDirectory(Path directoryPath) {
      return Files.exists(directoryPath) && Files.isDirectory(directoryPath);
    }

    private static void startWatching(WatchService watchService, String fileName){
        boolean valid = true;
        while (valid) {
            WatchKey key = null;
            try {
                key = watchService.take();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            for(WatchEvent<?> watchEvent: key.pollEvents()) {
                WatchEvent.Kind<?> kind = watchEvent.kind();
                Path filePath = ((WatchEvent<Path>) watchEvent).context();

                if (kind == ENTRY_MODIFY && filePath.toString().equals(fileName)) {
                    logger.info("File: " + filePath + " has been changed!");
                    onContentFileChanged();
                }
            }
            valid = key.reset();
        }
    }

    private static void onContentFileChanged(){
        ProcessDataService.getInstance().getAndProcessData();
    }
}