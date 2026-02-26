package by.glesap.task3.entity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Storage {
  private static final Logger logger = LoggerFactory.getLogger(Storage.class);
  private static Storage instance;
  private final int unloadCapacity;
  private final int loadCapacity;
  private final AtomicInteger loadContainerAmount;
  private final AtomicInteger unloadContainerAmount;
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
      } finally {
        lock.unlock();
      }
    }
    return instance;
  }

  private Storage(int capacity) {
    this.unloadCapacity = capacity;
    this.unloadContainerAmount = new AtomicInteger(0);
    this.loadCapacity = capacity;
    this.loadContainerAmount = new AtomicInteger(capacity);
  }

  public int getLoadCapacity() {
    return loadCapacity;
  }

  public int getUnloadCapacity() {
    return unloadCapacity;
  }

  public int getLoadContainerAmount() {
    return loadContainerAmount.get();
  }

  public void setLoadContainerAmount(int loadContainerAmount) {
    this.loadContainerAmount.set(Math.min(loadContainerAmount, loadCapacity));
  }

  public int getUnloadContainerAmount() {
    return unloadContainerAmount.get();
  }

  public void setUnloadContainerAmount(int unloadContainerAmount) {
    this.unloadContainerAmount.set(Math.max(unloadContainerAmount, 0));
  }

  public void addContainers(int amount) {
    try {
      lock.lock();
      while (amount > 0) {
        if (unloadContainerAmount.get() + amount < unloadCapacity) {
          logger.info("Adding {} containers to unload storage in one step", amount);
          unloadContainerAmount.addAndGet(amount);
          amount = 0;
        } else {
          int availableSpace = unloadCapacity - unloadContainerAmount.get();
          if (availableSpace == 0) {
            continue;
          }
          if (availableSpace > amount) {
            availableSpace = amount;
          }
          logger.info("Adding {} containers to unload storage", availableSpace);
          unloadContainerAmount.addAndGet(availableSpace);
          amount -= availableSpace;
          logger.info("{} containers are remaining to unload", amount);
          TimeUnit.SECONDS.sleep(1);
        }
      }
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }
  }

  public void borrowContainers(int amountToBorrow) {
    try {
      lock.lock();
      while (amountToBorrow > 0) {
        if (loadContainerAmount.get() >= amountToBorrow) {
          logger.info("Borrowed {} containers in one step. Remaining containers: {}",
                  amountToBorrow, unloadContainerAmount.get());
          loadContainerAmount.addAndGet(-amountToBorrow);
          amountToBorrow = 0;
        } else {
          int availableContainers = loadContainerAmount.get();
          if (availableContainers == 0) {
            continue;
          }
          if (availableContainers > amountToBorrow) {
            availableContainers = amountToBorrow;
          }
          loadContainerAmount.addAndGet(-availableContainers);
          amountToBorrow -= availableContainers;
          logger.info("Borrowed {} containers. Waiting for {} containers to borrow",
                  availableContainers, amountToBorrow);
          TimeUnit.SECONDS.sleep(1);
        }
      }
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    } finally {
      lock.unlock();
    }
  }

}
