package by.glesap.task3.util;

import by.glesap.task3.entity.Storage;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageLoader extends Thread {
  private static final Logger logger = LoggerFactory.getLogger(StorageLoader.class);
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final Storage storage = Storage.getInstance();
  private static final Random random = new Random();

  @Override
  public void run() {
    scheduler.scheduleAtFixedRate(() -> {
      int amountToLoad = random.nextInt(storage.getLoadCapacity());
      int amountToUnload = random.nextInt(storage.getUnloadCapacity());
      storage.setLoadContainerAmount(storage.getLoadContainerAmount() + amountToLoad);
      storage.setUnloadContainerAmount(storage.getUnloadContainerAmount() - amountToUnload);
      logger.info("Storage loaded. Amount added to load storage: {}. Amount decreased"
              + " from unload storage: {}", amountToLoad, amountToUnload);
    }, 0, 10, TimeUnit.SECONDS);
  }
}
