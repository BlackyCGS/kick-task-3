package by.glesap.task3.service;

import by.glesap.task3.entity.Ship;

public interface ShipLoadService {
  void loadShip(Ship ship);

  void unloadShip(Ship ship);
}
