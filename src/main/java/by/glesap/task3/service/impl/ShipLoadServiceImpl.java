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
    logger.info("Starting loading ship {}", ship.getShipName());
    int amountToLoad = ship.getCapacity() - ship.getContainerAmount();
    try{
        ship.load(amountToLoad);
        storage.borrowContainers(amountToLoad);
    }
    catch(DockException e) {
      logger.error(e.getMessage());
    }

    logger.info("Ship {} loaded", ship.getShipName());
  }

  @Override
  public void unloadShip(Ship ship) {
    logger.info("Starting unloading ship {}", ship.getShipName());
    int amount = ship.getContainerAmount();
    try {
        storage.addContainers(amount);
        ship.unload(amount);
      }
      catch (DockException e) {
        logger.error(e.getMessage());
      }
    logger.info("Ship {} unloaded", ship.getShipName());
  }
}
