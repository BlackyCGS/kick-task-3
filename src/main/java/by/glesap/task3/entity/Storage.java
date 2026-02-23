package by.glesap.task3.entity;

import by.glesap.task3.exception.DockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
  private static final Logger logger = LoggerFactory.getLogger(Storage.class);
  private static Storage instance;
  private final int unloadCapacity;
  private int loadContainerAmount;
  private int unloadContainerAmount;
  private static final ReentrantLock lock = new ReentrantLock();
  private static final AtomicBoolean isCreated = new AtomicBoolean(false);

  public static Storage getInstance() {
    if (!isCreated.get()) {
      try {
        lock.lock();
        if (instance == null) {
          logger.info("Creating new instance of Storage");
          instance = new Storage(100);
          isCreated.set(true);
        }
      }
      finally {
        lock.unlock();
      }
    }
    return instance;
  }

  private Storage(int capacity) {
    this.unloadCapacity = capacity;
  }

  public int getUnloadCapacity() {
    return unloadCapacity;
  }

  public int getLoadContainerAmount() {
    return loadContainerAmount;
  }

  public void setLoadContainerAmount(int loadContainerAmount) {
    this.loadContainerAmount = loadContainerAmount;
  }

  public int getUnloadContainerAmount() {
    return unloadContainerAmount;
  }

  public void setUnloadContainerAmount(int unloadContainerAmount) {
    this.unloadContainerAmount = unloadContainerAmount;
  }

  public void addContainers(int amount) throws DockException {
    if (unloadContainerAmount + amount > unloadCapacity) {
      throw new DockException("Storage can't have more than" + unloadCapacity +
              "containers");
    }
    unloadContainerAmount += amount;
  }

  public void borrowContainers(int amount) throws DockException {
    if (loadContainerAmount < amount) {
      throw new DockException("Not enough containers");
    }
    loadContainerAmount -= amount;
  }

}
