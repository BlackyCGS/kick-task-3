package by.glesap.task3.entity.impl;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.entity.ShipState;
import by.glesap.task3.service.ShipLoadService;
import by.glesap.task3.service.impl.ShipLoadServiceImpl;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipDockedState implements ShipState {
  private static final Logger logger = LoggerFactory.getLogger(ShipDockedState.class);
  private final ShipLoadService shipLoadService = new ShipLoadServiceImpl();

  @Override
  public void handle(Ship ship) {
    try {
      if (ship.getContainerAmount() > 0) {
        shipLoadService.unloadShip(ship);
      }
      shipLoadService.loadShip(ship);
      TimeUnit.MILLISECONDS.sleep(500);
      logger.info("Ship {} docked", ship.getShipName());
      ship.setShipState(new ShipDepartedState());
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
