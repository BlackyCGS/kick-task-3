package by.glesap.task3.entity.impl;

import by.glesap.task3.entity.Dock;
import by.glesap.task3.entity.Ship;
import by.glesap.task3.entity.ShipState;
import by.glesap.task3.service.DockService;
import by.glesap.task3.service.impl.DockServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ShipArrivedState implements ShipState {
  private static final Logger logger = LoggerFactory.getLogger(ShipArrivedState.class);
  private final DockService dockService = new DockServiceImpl();
  @Override
  public void handle(Ship ship) {
    try {
      TimeUnit.SECONDS.sleep(3);
      Dock dock = dockService.requestFreeDock();
      dockService.dockShip(ship, dock);
      logger.info("Ship {} arrived", ship.getShipName());
      ship.setShipState(new ShipDockedState());
    }
    catch (InterruptedException e) {
      logger.error(e.getMessage());
    }

  }
}
