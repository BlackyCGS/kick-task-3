package by.glesap.task3.service.impl;

import by.glesap.task3.entity.Dock;
import by.glesap.task3.entity.Ship;
import by.glesap.task3.repository.DockRepository;
import by.glesap.task3.service.DockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DockServiceImpl implements DockService {
  private static final Logger logger = LoggerFactory.getLogger(DockServiceImpl.class);
  private final DockRepository repository = DockRepository.getInstance();

  @Override
  public Dock requestFreeDock() {
    logger.info("Free dock requested");
    Dock dock = repository.getFreeDock();
    while (dock == null) {
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      }
      catch (InterruptedException e) {
        logger.error(e.getMessage());
      }
      dock = repository.getFreeDock();
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
