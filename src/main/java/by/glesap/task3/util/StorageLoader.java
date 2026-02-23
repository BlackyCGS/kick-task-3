package by.glesap.task3.util;

import by.glesap.task3.entity.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StorageLoader extends Thread {
  private static final Logger logger = LoggerFactory.getLogger(StorageLoader.class);
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final Storage storage = Storage.getInstance();

  @Override
  public void run() {
    scheduler.scheduleAtFixedRate(() -> {
        storage.setLoadContainerAmount(storage.getLoadContainerAmount() + 10);
        if(storage.getUnloadContainerAmount() >10) {
          storage.setUnloadContainerAmount(storage.getUnloadContainerAmount() - 10);
        }
    },0, 2, TimeUnit.SECONDS);
  }
}
