package by.glesap.task3.entity.impl;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.entity.ShipState;
import by.glesap.task3.service.DockService;
import by.glesap.task3.service.impl.DockServiceImpl;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShipDepartedState implements ShipState {
  private static final Logger logger = LoggerFactory.getLogger(ShipDepartedState.class);
  private final DockService dockService = new DockServiceImpl();

  @Override
  public void handle(Ship ship) {
    try {
      TimeUnit.SECONDS.sleep(1);
      dockService.undockShip(ship);
      logger.info("Ship {} departed", ship.getShipName());
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
