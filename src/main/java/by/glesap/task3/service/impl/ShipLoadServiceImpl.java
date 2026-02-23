package by.glesap.task3.service.impl;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.entity.Storage;
import by.glesap.task3.exception.DockException;
import by.glesap.task3.service.ShipLoadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipLoadServiceImpl implements ShipLoadService {
  private static final Logger logger = LoggerFactory.getLogger(ShipLoadServiceImpl.class);
  private final Storage storage = Storage.getInstance();

  @Override
  public void loadShip(Ship ship) {
    int amountToLoad = ship.getCapacity() - ship.getContainerAmount();
    if(amountToLoad < storage.getLoadContainerAmount()) {
      try{
        ship.load(amountToLoad);
        storage.borrowContainers(amountToLoad);
      }
      catch(DockException e) {
        logger.error(e.getMessage());
      }
    }
  }

  @Override
  public void unloadShip(Ship ship) {
    int amount = ship.getContainerAmount();
    if (storage.getUnloadContainerAmount() - amount > 0) {
      try {
        ship.unload(amount);
        storage.addContainers(amount);
      }
      catch (DockException e) {
        logger.error(e.getMessage());
      }
    }
  }
}
