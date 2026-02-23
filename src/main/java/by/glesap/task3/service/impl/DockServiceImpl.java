package by.glesap.task3.service.impl;

import by.glesap.task3.entity.Dock;
import by.glesap.task3.entity.Ship;
import by.glesap.task3.repository.DockRepository;
import by.glesap.task3.service.DockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DockServiceImpl implements DockService {
  private static final Logger logger = LoggerFactory.getLogger(DockServiceImpl.class);
  private final DockRepository repository = DockRepository.getInstance();
  private final Lock lock = new ReentrantLock();

  @Override
  public Dock requestFreeDock() {
    logger.info("Free dock requested");
    Dock dock;
    try {
      lock.lock();
      do {
        dock = repository.getFreeDock();
      } while (dock == null);
    }
    finally {
      lock.unlock();
    }
    return dock;
  }

  @Override
  public void dockShip(Ship ship, Dock dock) {
    logger.info("Ship {} docked", ship.getShipName());
    ship.setCurrentDock(dock);
  }

  @Override
  public void undockShip(Ship ship) {
    logger.info("Ship {} undocked", ship.getShipName());
    Dock dock = ship.getCurrentDock();
    ship.setCurrentDock(null);
    repository.add(dock);
  }
}
