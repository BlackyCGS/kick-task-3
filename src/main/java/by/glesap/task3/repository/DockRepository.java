package by.glesap.task3.repository;

import by.glesap.task3.entity.Dock;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockRepository {
  private static final Logger logger = LoggerFactory.getLogger(DockRepository.class);
  private static DockRepository instance;
  private static final ReentrantLock lock = new ReentrantLock();
  private static final AtomicBoolean isCreated = new AtomicBoolean(false);
  private final LinkedList<Dock> docks = new LinkedList<>();

  public static DockRepository getInstance() {
    logger.info("DockRepository.getInstance()");
    if (!isCreated.get()) {
      try {
        lock.lock();
        if (instance == null) {
          instance = new DockRepository();
          isCreated.set(true);
        }
      }
      finally {
        lock.unlock();
      }
    }
    return instance;
  }

  private DockRepository() {
    docks.add(new Dock(1));
    docks.add(new Dock(2));
  }

  public void add(Dock dock) {
    logger.info("Added dock {} to repository", dock.getDockNumber());
    try {
      lock.lock();
      docks.addLast(dock);
    }
    finally {
      lock.unlock();
    }
  }

  public Dock getFreeDock() {
    try {
      lock.lock();
      return docks.pollFirst();
    }
    finally {
      lock.unlock();
    }
  }
}
