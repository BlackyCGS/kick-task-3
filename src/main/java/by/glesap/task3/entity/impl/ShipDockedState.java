package by.glesap.task3.entity.impl;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.entity.ShipState;
import by.glesap.task3.service.ShipLoadService;
import by.glesap.task3.service.impl.ShipLoadServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ShipDockedState implements ShipState {
  private static final Logger logger = LoggerFactory.getLogger(ShipDockedState.class);
  private final ShipLoadService shipLoadService = new ShipLoadServiceImpl();
  @Override
  public void handle(Ship ship) {
      try {
        if(ship.getContainerAmount() > 0) {
          shipLoadService.unloadShip(ship);
        }
          shipLoadService.loadShip(ship);
        TimeUnit.MILLISECONDS.sleep(500);
        logger.info("Ship {} docked", ship.getShipName());
        ship.setShipState(new ShipDepartedState());
      }
      catch (Exception e) {
        logger.error(e.getMessage());
      }
  }
}
