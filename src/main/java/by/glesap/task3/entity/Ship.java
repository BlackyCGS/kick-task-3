package by.glesap.task3.entity;

import by.glesap.task3.entity.impl.ShipArrivedState;
import by.glesap.task3.entity.impl.ShipDepartedState;
import by.glesap.task3.exception.DockException;
import java.util.concurrent.Callable;

public class Ship implements Callable<Void> {
  private String shipName;
  private int capacity;
  private int containerAmount;
  private ShipState shipState;
  private Dock currentDock;

  public Ship(String name, int capacity, int containerAmount) {
    this.shipName = name;
    this.capacity = capacity;
    this.containerAmount = containerAmount;
  }

  public String getShipName() {
    return shipName;
  }

  public void setShipName(String name) {
    this.shipName = name;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getContainerAmount() {
    return containerAmount;
  }

  public void unload(int amount) throws DockException {
    if (amount > containerAmount) {
      throw new DockException("amount is greater that amount of containers on ship");
    }
    containerAmount -= amount;
  }

  public void load(int amount) throws DockException {
    if (amount + containerAmount > capacity) {
      throw new DockException("amount is greater than capacity");
    }
    containerAmount += amount;
  }

  public ShipState getShipState() {
    return shipState;
  }

  public void setShipState(ShipState shipState) {
    this.shipState = shipState;
  }

  public Dock getCurrentDock() {
    return currentDock;
  }

  public void setCurrentDock(Dock currentDock) {
    this.currentDock = currentDock;
  }

  @Override
  public Void call() {
    this.setShipState(new ShipArrivedState());
    while (!(this.getShipState() instanceof ShipDepartedState)) {
      shipState.handle(this);
    }
    shipState.handle(this);
    return null;
  }
}
